package ru.polytech.smart.greenhouse.irrigation

import org.springframework.stereotype.Component
import ru.polytech.smart.greenhouse.bed.BedRepository
import java.util.*

@Component
class IrrigationMapper(
    private val bedRepository: BedRepository
) {

    fun toDto(entity: IrrigationScheduleEntity): IrrigationScheduleTo = IrrigationScheduleTo(
        id = entity.id,
        bedId = entity.bed?.id,
        startTime = entity.startTime,
        endTime = entity.endTime,
        isActive = entity.isActive,
        daysOfWeek = entity.daysOfWeek,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )

    fun toEntity(dto: IrrigationScheduleTo): IrrigationScheduleEntity {
        val bed = dto.bedId?.let {
            bedRepository.findById(it).orElseThrow {
                IllegalArgumentException("Bed with ID $it not found")
            }
        } ?: throw IllegalArgumentException("Bed ID must not be null")

        return IrrigationScheduleEntity(
            id = UUID.randomUUID(),
            bed = bed,
            startTime = dto.startTime ?: throw IllegalArgumentException("Start time must not be null"),
            endTime = dto.endTime ?: throw IllegalArgumentException("requiredVolumeLiters must not be null"),
            daysOfWeek = dto.daysOfWeek ?: throw IllegalArgumentException("daysOfWeek must not be null"),
            isActive = dto.isActive ?: false,
        )
    }

    fun updateEntityFromDto(entity: IrrigationScheduleEntity, dto: IrrigationScheduleTo) {
        dto.startTime?.let { entity.startTime = it }
        dto.endTime?.let { entity.endTime = it }

        dto.bedId?.let {
            entity.bed = bedRepository.findById(it).orElseThrow {
                IllegalArgumentException("Bed with ID $it not found")
            }
        }
    }

    fun toDto(entities: List<IrrigationScheduleEntity>): List<IrrigationScheduleTo> =
        entities.map(this::toDto)
}
