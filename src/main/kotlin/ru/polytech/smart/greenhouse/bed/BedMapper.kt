package ru.polytech.smart.greenhouse.bed

import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.springframework.beans.factory.annotation.Autowired
import ru.polytech.smart.greenhouse.crop.CropsRepository
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseRepository

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
abstract class BedMapper {

    @Autowired
    protected lateinit var greenhouseRepository: GreenhouseRepository
    @Autowired
    protected lateinit var cropsRepository: CropsRepository

    @Mapping(target = "greenhouseId", source = "greenhouse.id")
    @Mapping(target = "cropId", source = "crop.id")
    @Mapping(target = "deviceIds", expression = "java(entity.getDevices().stream().map(ru.polytech.smart.greenhouse.device.DeviceEntity::getId).collect(java.util.stream.Collectors.toList()))")
    abstract fun toDto(entity: BedEntity): BedTo

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "greenhouse", ignore = true)
    @Mapping(target = "crop", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    abstract fun toEntity(bedTo: BedTo): BedEntity

    abstract fun toDto(bedEntities: List<BedEntity>): List<BedTo>

    @AfterMapping
    fun afterMapping(bedTo: BedTo, @MappingTarget entity: BedEntity) {
        bedTo.greenhouseId?.let { entity.greenhouse = greenhouseRepository.getReferenceById(it) }
        bedTo.cropId?.let { entity.crop = cropsRepository.getReferenceById(it) }
    }
}