package ru.polytech.smart.greenhouse.irrigation

import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Service

//@Service
//@EnableScheduling
//class IrrigationScheduler(
//    private val scheduleRepository: IrrigationScheduleRepository,
//    private val mqttService: IrrigationMqttService
//) {
//    private val logger = LoggerFactory.getLogger(javaClass)
//
//    @Scheduled(cron = "\${irrigation.scheduler.cron:0 */5 * * * *}")
//    fun checkSchedules() {
//        val activeSchedules = scheduleRepository.findByIsActive(true)
//        val now = LocalDateTime.now()
//
//        activeSchedules.forEach { schedule ->
//            if (shouldTrigger(schedule, now)) {
//                try {
//                    mqttService.sendIrrigationCommand(
//                        bedId = schedule.bed!!.id,
//                        volumeLiters = schedule.requiredVolumeLiters
//                    )
//                    logger.info("Triggered irrigation for schedule ${schedule.id}")
//                } catch (e: Exception) {
//                    logger.error("Failed to trigger irrigation for schedule ${schedule.id}", e)
//                }
//            }
//        }
//    }
//
//    private fun shouldTrigger(schedule: IrrigationScheduleEntity, now: LocalDateTime): Boolean {
//        val currentDayOfWeek = now.dayOfWeek.value
//        val days = schedule.daysOfWeek.split(",").map { it.toInt() }
//        val timeMatches = schedule.startTime.truncatedTo(ChronoUnit.MINUTES) ==
//                now.toLocalTime().truncatedTo(ChronoUnit.MINUTES)
//
//        return days.contains(currentDayOfWeek) && timeMatches
//    }
//}