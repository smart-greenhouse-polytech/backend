package ru.polytech.smart.greenhouse.irrigation.impl

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleController
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleEntity
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleService
import java.util.UUID

@RestController
@RequestMapping("/api/v1/irrigation-schedules")
class IrrigationScheduleControllerImpl(
    private val irrigationService: IrrigationScheduleService
) : IrrigationScheduleController {

    override fun createSchedule(entity: IrrigationScheduleEntity) =
        ResponseEntity.ok(irrigationService.createSchedule(entity))

    override fun getSchedule(id: UUID) =
        ResponseEntity.ok(irrigationService.getSchedule(id))

    override fun getAllSchedules() =
        ResponseEntity.ok(irrigationService.getAllSchedules())

    override fun updateSchedule(id: UUID, entity: IrrigationScheduleEntity) =
        ResponseEntity.ok(irrigationService.updateSchedule(id, entity))

    override fun deleteSchedule(id: UUID): ResponseEntity<Void> {
        irrigationService.deleteSchedule(id)
        return ResponseEntity.noContent().build()
    }

    override fun toggleSchedule(id: UUID) =
        ResponseEntity.ok(irrigationService.toggleSchedule(id))
}