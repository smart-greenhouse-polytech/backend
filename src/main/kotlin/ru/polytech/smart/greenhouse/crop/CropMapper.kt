package ru.polytech.smart.greenhouse.crop

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
interface CropMapper {
    fun toDto(entity: CropEntity): CropTo

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    fun toEntity(bedTo: CropTo): CropEntity

    fun toDto(bedEntities: List<CropEntity>): List<CropTo>
}