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
    private val logger = LoggerFactory.getLogger(javaClass)
    private val activeSchedules = ConcurrentHashMap<UUID, ScheduledFuture<*>>()

    @PostConstruct
    fun init() {
        loadActiveSchedules()
    }

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

        val task = IrrigationTask(
            schedule = schedule,
            deviceControlService = deviceControlService,
            bedRepository = bedRepository
        )

        val trigger = IrrigationTrigger(
            daysOfWeek = schedule.daysOfWeek,
            time = schedule.startTime
        )

        val scheduledTask = taskScheduler.schedule(task, trigger)
        scheduledTask?.let {
            activeSchedules[schedule.id] = it
            logger.info("Scheduled irrigation for bed ${schedule.bed?.id} at ${schedule.startTime} on ${schedule.daysOfWeek}")
        }
    }

    fun cancelSchedule(scheduleId: UUID) {
        activeSchedules[scheduleId]?.let {
            it.cancel(true)
            activeSchedules.remove(scheduleId)
            logger.info("Cancelled irrigation schedule $scheduleId")
        }
    }

    fun updateSchedule(schedule: IrrigationScheduleEntity) {
        if (schedule.isActive) {
            scheduleIrrigation(schedule)
        } else {
            cancelSchedule(schedule.id)
        }
    }

    inner class IrrigationTask(
        private val schedule: IrrigationScheduleEntity,
        private val deviceControlService: DeviceControlService,
        private val bedRepository: BedRepository
    ) : Runnable {
        override fun run() {
            val bed = schedule.bed ?: run {
                logger.error("Bed not found for schedule ${schedule.id}")
                return
            }

            val deviceName = schedule.bed?.devices?.find { x -> x.type == DeviceType.WATER_VALVE }?.name
                ?: throw RuntimeException("Device name not found for schedule ${schedule.id}")


            try {
                val valveDevice = DeviceIdentifier(
                    deviceType = DeviceType.WATER_VALVE,
                    deviceName = deviceName
                )

                logger.info("Starting irrigation for bed ${bed.id} (${bed.name})")
                deviceControlService.sendCommand(valveDevice, "1")

                // Запланировать выключение по endTime
                val duration = Duration.between(schedule.startTime, schedule.endTime)
                taskScheduler.schedule(
                    {
                        deviceControlService.sendCommand(valveDevice, "0")
                        logger.info("Finished irrigation for bed ${bed.id}")
                    },
                    Instant.now().plus(duration)
                )

                // Обновим статус грядки
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
            val now = LocalDateTime.now()
            var nextRun = now.with(time)

            // Если время уже прошло сегодня, планируем на следующий день
            if (nextRun.isBefore(now)) {
                nextRun = nextRun.plusDays(1)
            }

            // Ищем ближайший день недели из расписания
            while (!daysOfWeek.contains(nextRun.dayOfWeek)) {
                nextRun = nextRun.plusDays(1)
            }

            return nextRun.atZone(ZoneId.systemDefault()).toInstant()
        }
    }
}