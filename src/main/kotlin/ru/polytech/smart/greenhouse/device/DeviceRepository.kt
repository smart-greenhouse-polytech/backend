package ru.polytech.smart.greenhouse.device

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.polytech.smart.greenhouse.device.DeviceEntity
import ru.polytech.smart.greenhouse.device.DeviceType
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseEntity
import java.util.*

@Repository
interface DeviceRepository : JpaRepository<DeviceEntity, UUID> {

    fun findByName(name: String): Optional<DeviceEntity>

    @Query("SELECT d FROM DeviceEntity d WHERE d.greenhouse = :greenhouse")
    fun findByGreenhouse(greenhouse: GreenhouseEntity): List<DeviceEntity>

    @Query("SELECT d FROM DeviceEntity d WHERE d.type IN :types")
    fun findByTypeIn(types: List<DeviceType>): List<DeviceEntity>

    @Query("SELECT d FROM DeviceEntity d WHERE d.bed.id = :bedId")
    fun findByBedId(bedId: UUID): List<DeviceEntity>

    @Query("SELECT d FROM DeviceEntity d WHERE d.greenhouse.id = :greenhouseId AND d.type = :type")
    fun findByGreenhouseIdAndType(greenhouseId: UUID, type: DeviceType): List<DeviceEntity>
}