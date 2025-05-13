package ru.polytech.smart.greenhouse.mqtt

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.TriggerContext
import org.springframework.stereotype.Service
import ru.polytech.smart.greenhouse.bed.BedRepository
import ru.polytech.smart.greenhouse.device.DeviceIdentifier
import ru.polytech.smart.greenhouse.device.DeviceType
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleEntity
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleRepository
import java.time.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ScheduledFuture

@Service
class IrrigationService(
    private val scheduleRepository: IrrigationScheduleRepository,
    private val deviceControlService: DeviceControlService,
    private val taskScheduler: TaskScheduler,
    private val bedRepository: BedRepository
) {
    private val logger = LoggerFactory.getLogger(IrrigationService::class.java)
    private val activeSchedules = ConcurrentHashMap<UUID?, ScheduledFuture<*>>()

    @PostConstruct
    fun init() = loadActiveSchedules()

    fun loadActiveSchedules() {
        scheduleRepository.findAllByIsActive(true).forEach { schedule ->
            try {
                scheduleIrrigation(schedule)
            } catch (e: Exception) {
                logger.error("Failed to schedule irrigation for bed ${schedule.bed?.id}", e)
            }
        }
    }

    fun scheduleIrrigation(schedule: IrrigationScheduleEntity) {
        cancelSchedule(schedule.id)

        if (!schedule.isActive || schedule.bed == null) return

        val duration = Duration.between(schedule.startTime, schedule.endTime).seconds
        if (duration <= 0) {
            logger.warn("Invalid irrigation duration for schedule ${schedule.id}")
            return
        }

        val task = IrrigationTask(schedule)
        val trigger = IrrigationTrigger(schedule.daysOfWeek, schedule.startTime)

        val scheduledTask = taskScheduler.schedule(task, trigger)
        scheduledTask?.let {
            activeSchedules[schedule.id] = it
            logger.info("Scheduled irrigation for bed ${schedule.bed?.id} at ${schedule.startTime} on ${schedule.daysOfWeek}")
        }
    }

    fun cancelSchedule(scheduleId: UUID?) {
        activeSchedules.remove(scheduleId)?.also {
            it.cancel(true)
            logger.info("Cancelled irrigation schedule $scheduleId")
        }
    }

    fun updateSchedule(schedule: IrrigationScheduleEntity) {
        if (schedule.isActive) scheduleIrrigation(schedule)
        else cancelSchedule(schedule.id)
    }

    inner class IrrigationTask(
        private val schedule: IrrigationScheduleEntity
    ) : Runnable {
        override fun run() {
            val bed = schedule.bed ?: run {
                logger.error("Bed not found for schedule ${schedule.id}")
                return
            }

            val device = schedule.bed?.devices
                ?.find { it?.type == DeviceType.WATER_VALVE }
                ?: run {
                    logger.error("Water valve device not found for schedule ${schedule.id}")
                    return
                }

            try {
                val valveDevice = DeviceIdentifier(device.type, device.name)

                logger.info("Starting irrigation for bed ${bed.id} (${bed.name})")
                deviceControlService.sendCommand(valveDevice, "1")

                // Расчёт корректного времени с учётом возможного перехода через полночь
                val duration = Duration.between(schedule.startTime, schedule.endTime).let {
                    if (it.isNegative || it.isZero) it.plusHours(24) else it
                }

                taskScheduler.schedule(
                    {
                        deviceControlService.sendCommand(valveDevice, "0")
                        logger.info("Finished irrigation for bed ${bed.id}")
                    },
                    Instant.now().plus(duration)
                )

                bed.lastIrrigation = LocalDateTime.now()
                bedRepository.save(bed)

            } catch (e: Exception) {
                logger.error("Error during irrigation for bed ${bed.id}", e)
            }
        }
    }

    inner class IrrigationTrigger(
        private val daysOfWeek: Set<DayOfWeek>,
        private val time: LocalTime
    ) : Trigger {
        override fun nextExecution(triggerContext: TriggerContext): Instant? {
            var nextRun = LocalDateTime.now().with(time)
            if (nextRun.isBefore(LocalDateTime.now())) nextRun = nextRun.plusDays(1)

            while (nextRun.dayOfWeek !in daysOfWeek) {
                nextRun = nextRun.plusDays(1)
            }

            return nextRun.atZone(ZoneId.systemDefault()).toInstant()
        }
    }
}
