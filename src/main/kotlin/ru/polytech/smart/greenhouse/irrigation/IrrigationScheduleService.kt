package ru.polytech.smart.greenhouse.irrigation

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.polytech.smart.greenhouse.bed.BedRepository
import java.util.*

@Service
class IrrigationScheduleService(
    private val repository: IrrigationScheduleRepository,
    private val bedRepository: BedRepository
) {

    fun createSchedule(dto: IrrigationScheduleEntity): IrrigationScheduleEntity {
        val bed = bedRepository.findById(dto.bedId)
            .orElseThrow { EntityNotFoundException("Грядка с ID ${dto.bedId} не найдена") }

        val schedule = IrrigationScheduleEntity(
            bed = bed,
            daysOfWeek = dto.daysOfWeek,
            startTime = dto.startTime,
            requiredVolumeLiters = dto.requiredVolumeLiters,
            isActive = dto.isActive
        )

        val saved = repository.save(schedule)
        return toResponse(saved)
    }

    fun getSchedule(id: UUID): IrrigationScheduleEntity {
        val schedule = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Расписание с ID $id не найдено") }
        return toResponse(schedule)
    }

    fun getAllSchedules(): List<IrrigationScheduleEntity> {
        return repository.findAll().map { toResponse(it) }
    }

    fun getSchedulesForBed(bedId: UUID): List<IrrigationScheduleEntity> {
        return repository.findByBedId(bedId).map { toResponse(it) }
    }

    fun updateSchedule(id: UUID, dto: IrrigationScheduleEntity): IrrigationScheduleEntity {
        val schedule = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Расписание с ID $id не найдено") }

        val bed = bedRepository.findById(dto.bed.id)
            .orElseThrow { EntityNotFoundException("Грядка с ID ${dto.bedId} не найдена") }

        schedule.apply {
            this.bed = bed
            this.daysOfWeek = dto.daysOfWeek
            this.startTime = dto.startTime
            this.requiredVolumeLiters = dto.requiredVolumeLiters
            this.isActive = dto.isActive
        }

        val updated = repository.save(schedule)
        return toResponse(updated)
    }

    fun deleteSchedule(id: UUID) {
        repository.deleteById(id)
    }

    fun toggleSchedule(id: UUID): IrrigationScheduleEntity {
        val schedule = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Расписание с ID $id не найдено") }

        schedule.isActive = !schedule.isActive
        val updated = repository.save(schedule)
        return toResponse(updated)
    }

    private fun toResponse(entity: IrrigationScheduleEntity): IrrigationScheduleEntity {
        return IrrigationScheduleEntity(
            id = entity.id,
            bedId = entity.bed?.id ?: throw IllegalStateException("Грядка не указана"),
            daysOfWeek = entity.daysOfWeek,
            startTime = entity.startTime,
            requiredVolumeLiters = entity.requiredVolumeLiters,
            isActive = entity.isActive,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}