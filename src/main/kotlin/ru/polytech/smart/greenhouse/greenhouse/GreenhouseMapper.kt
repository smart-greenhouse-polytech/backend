package ru.polytech.smart.greenhouse.greenhouse

import org.springframework.stereotype.Component

@Component
class GreenhouseMapper(
    private val greenhouseSettingRepository: GreenhouseSettingRepository
) {

    fun toDto(entity: GreenhouseEntity): GreenhouseTo = GreenhouseTo(
        id = entity.id,
        name = entity.name,
        greenhouseSettingId = entity.greenhouseSetting?.id,
        location = entity.location,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )

    fun toEntity(dto: GreenhouseTo): GreenhouseEntity {
        val entity = GreenhouseEntity(
            name = dto.name ?: throw IllegalArgumentException("Greenhouse name must not be null"),
            greenhouseSetting = null,
            location = dto.location ?: throw IllegalArgumentException("Greenhouse location must not be null"),
        )

        dto.greenhouseSettingId?.let {
            entity.greenhouseSetting = greenhouseSettingRepository.findById(it)
                .orElseThrow { IllegalArgumentException("GreenhouseSetting with ID $it not found") }
        }

        return entity
    }

    fun updateEntityFromDto(entity: GreenhouseEntity, dto: GreenhouseTo) {
        dto.name?.let { entity.name = it }

        dto.greenhouseSettingId?.let {
            entity.greenhouseSetting = greenhouseSettingRepository.findById(it)
                .orElseThrow { IllegalArgumentException("GreenhouseSetting with ID $it not found") }
        }
    }

    fun toDto(entities: List<GreenhouseEntity>): List<GreenhouseTo> = entities.map(this::toDto)
}
