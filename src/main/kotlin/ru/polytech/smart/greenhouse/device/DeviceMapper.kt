package ru.polytech.smart.greenhouse.device

import org.springframework.stereotype.Component
import ru.polytech.smart.greenhouse.bed.BedRepository
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseRepository
import java.util.*

@Component
class DeviceMapper(
    private val greenhouseRepository: GreenhouseRepository,
    private val bedRepository: BedRepository
) {

    fun toDto(entity: DeviceEntity): DeviceTo = DeviceTo(
        id = entity.id,
        name = entity.name,
        type = entity.type,
        greenhouseId = entity.greenhouse?.id,
        bedId = entity.bed?.id,
        status = entity.status,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )

    fun toEntity(dto: DeviceTo): DeviceEntity {
        val entity = DeviceEntity(
            name = dto.name ?: throw IllegalArgumentException("Device name must not be null"),
            type = dto.type ?: throw IllegalArgumentException("Device type must not be null"),
            greenhouse = null,
            bed = null,
        )

        dto.greenhouseId?.let {
            entity.greenhouse = greenhouseRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Greenhouse with ID $it not found") }
        }

        dto.bedId?.let {
            entity.bed = bedRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Bed with ID $it not found") }
        }

        return entity
    }

    fun updateEntityFromDto(entity: DeviceEntity, dto: DeviceTo) {
        dto.name?.let { entity.name = it }
        dto.type?.let { entity.type = it }

        dto.greenhouseId?.let {
            entity.greenhouse = greenhouseRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Greenhouse with ID $it not found") }
        }

        dto.bedId?.let {
            entity.bed = bedRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Bed with ID $it not found") }
        }
    }

    fun toDto(entities: List<DeviceEntity>): List<DeviceTo> = entities.map(this::toDto)
}
