package ru.polytech.smart.greenhouse.device

import org.springframework.stereotype.Component

@Component
class DeviceMeasurementMapper {

    fun toDto(entity: DeviceMeasurementEntity): DeviceMeasurementTo = DeviceMeasurementTo(
        id = entity.id,
        deviceId = entity.device.id,
        value = entity.value,
        createdAt = entity.createdAt
    )

    fun toDto(entities: List<DeviceMeasurementEntity>): List<DeviceMeasurementTo> =
        entities.map(this::toDto)
}
