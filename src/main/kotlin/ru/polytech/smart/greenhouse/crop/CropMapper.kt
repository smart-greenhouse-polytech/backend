package ru.polytech.smart.greenhouse.crop

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CropMapper {

    fun toDto(entity: CropEntity): CropTo = CropTo(
        id = entity.id,
        name = entity.name,
        tempMin = entity.tempMin,
        tempMax = entity.tempMax,
        waterRequirementLiters = entity.waterRequirementLiters,
    )

    fun toEntity(to: CropTo): CropEntity = CropEntity(
        name = to.name ?: throw IllegalArgumentException("Crop name must not be null"),
        tempMin = to.tempMin ?: throw IllegalArgumentException("Crop tempMin must not be null"),
        tempMax = to.tempMax ?: throw IllegalArgumentException("Crop tempMax must not be null"),
        waterRequirementLiters = to.waterRequirementLiters ?: throw IllegalArgumentException("Crop waterRequirementLiters must not be null"),
    )

    fun updateEntityFromDto(entity: CropEntity, dto: CropTo) {
        dto.name?.let { entity.name = it }
        dto.tempMin?.let { entity.tempMin = it }
        dto.tempMax?.let { entity.tempMax = it }
        dto.waterRequirementLiters?.let { entity.waterRequirementLiters = it }
    }

    fun toDto(entities: List<CropEntity>): List<CropTo> = entities.map(this::toDto)
}
