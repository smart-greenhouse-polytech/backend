package ru.polytech.smart.greenhouse.greenhouse

import org.springframework.stereotype.Component
import java.util.*

@Component
class GreenhouseSettingMapper {

    fun toDto(entity: GreenhouseSettingEntity): GreenhouseSettingTo = GreenhouseSettingTo(
        id = entity.id,
        tempMin = entity.tempMin,
        tempMax = entity.tempMax,
        humidityMin = entity.humidityMin,
        humidityMax = entity.humidityMax,
        lightIntensityMax = entity.lightIntensityMax,
        lightIntensityMin = entity.lightIntensityMin,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )

    fun toEntity(dto: GreenhouseSettingTo): GreenhouseSettingEntity = GreenhouseSettingEntity(
        id = UUID.randomUUID(),
        tempMin = dto.tempMin ?: throw IllegalArgumentException("tempMin must not be null"),
        tempMax = dto.tempMax ?: throw IllegalArgumentException("tempMax must not be null"),
        humidityMin = dto.humidityMin ?: throw IllegalArgumentException("humidityMin must not be null"),
        humidityMax = dto.humidityMax ?: throw IllegalArgumentException("humidityMax must not be null"),
        lightIntensityMin = dto.lightIntensityMin ?: throw IllegalArgumentException("lightIntensityMin must not be null"),
        lightIntensityMax = dto.lightIntensityMax ?: throw IllegalArgumentException("lightIntensityMax must not be null"),
    )

    fun updateSettingFromDto(entity: GreenhouseSettingEntity, dto: GreenhouseSettingTo) {
        dto.tempMin?.let { entity.tempMin = it }
        dto.tempMax?.let { entity.tempMax = it }
        dto.humidityMin?.let { entity.humidityMin = it }
        dto.humidityMax?.let { entity.humidityMax = it }
    }

    fun toDto(entities: List<GreenhouseSettingEntity>): List<GreenhouseSettingTo> =
        entities.map(this::toDto)
}
