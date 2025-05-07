package ru.polytech.smart.greenhouse.bed

import org.springframework.stereotype.Component
import ru.polytech.smart.greenhouse.crop.CropsRepository
import ru.polytech.smart.greenhouse.device.DeviceRepository
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseRepository

@Component
class BedMapper(
    private val greenhouseRepository: GreenhouseRepository,
    private val cropsRepository: CropsRepository,
    private val deviceRepository: DeviceRepository
) {

    fun toDto(entity: BedEntity): BedTo = BedTo(
        greenhouseId = entity.greenhouse?.id,
        name = entity.name,
        cropId = entity.crop?.id,
        deviceIds = entity.devices?.map { it.id } ?: emptyList(),
        lastIrrigation = entity.lastIrrigation,
    )

    fun toDto(entities: List<BedEntity>): List<BedTo> = entities.map { toDto(it) }

    fun toEntity(to: BedTo): BedEntity {
        return BedEntity(
            greenhouse = null,      // будет заполняться отдельно
            name = to.name ?: throw IllegalArgumentException("Name must not be null"),
            crop = null,
            devices = mutableListOf(),
            lastIrrigation = null
        ).apply {
            updateEntityFromDto(this, to)
        }
    }

    fun updateEntityFromDto(entity: BedEntity, dto: BedTo) {
        dto.name?.let { entity.name = it }

        dto.greenhouseId?.let {
            entity.greenhouse = greenhouseRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Greenhouse with ID $it not found") }
        }

        dto.cropId?.let {
            entity.crop = cropsRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Crop with ID $it not found") }
        }

        dto.deviceIds?.takeIf { it.isNotEmpty() }?.let { deviceIds ->
            entity.devices = deviceIds.map { deviceId ->
                deviceRepository.findById(deviceId)
                    .orElseThrow { IllegalArgumentException("Device with ID $deviceId not found") }
            }.toMutableList()
        }
    }
}
