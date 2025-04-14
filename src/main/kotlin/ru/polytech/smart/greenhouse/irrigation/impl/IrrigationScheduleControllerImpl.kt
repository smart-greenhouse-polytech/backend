package ru.polytech.smart.greenhouse.irrigation.impl

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.irrigation.IrrigationMapper
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleController
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleRepository
import ru.polytech.smart.greenhouse.irrigation.IrrigationScheduleTo
import java.util.UUID

@RestController
class IrrigationScheduleControllerImpl(
    private val scheduleRepository: IrrigationScheduleRepository,
    private val irrigationMapper: IrrigationMapper
) : IrrigationScheduleController {

    override fun createSchedule(dto: IrrigationScheduleTo): ResponseEntity<IrrigationScheduleTo> {
        val entity = irrigationMapper.toEntity(dto)
        return ResponseEntity.ok(irrigationMapper.toDto(scheduleRepository.save(entity)))
    }

    override fun getAllSchedules(): List<IrrigationScheduleTo> {
        return scheduleRepository.findAll().map { irrigationMapper.toDto(it) }
    }

    override fun getSchedule(id: UUID): ResponseEntity<IrrigationScheduleTo> {
        return scheduleRepository.findById(id)
            .map { ResponseEntity.ok(irrigationMapper.toDto(it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    override fun updateSchedule(
        id: UUID,
        dto: IrrigationScheduleTo
    ): ResponseEntity<IrrigationScheduleTo> {
        return scheduleRepository.findById(id)
            .map { existing ->
                irrigationMapper.updateEntityFromDto(existing, dto)
                ResponseEntity.ok(irrigationMapper.toDto(scheduleRepository.save(existing)))
            }.orElse(ResponseEntity.notFound().build())
    }

    override fun deleteSchedule(id: UUID): ResponseEntity<Void> {
        return if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}