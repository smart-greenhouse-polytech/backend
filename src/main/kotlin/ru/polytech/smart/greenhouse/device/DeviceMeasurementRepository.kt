package ru.polytech.smart.greenhouse.device

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface DeviceMeasurementRepository : JpaRepository<DeviceMeasurementEntity, UUID> {

    @Query("SELECT m FROM DeviceMeasurementEntity m WHERE m.device = :device ORDER BY m.createdAt DESC")
    fun findLatestByDevice(device: DeviceEntity): List<DeviceMeasurementEntity>

    @Query("SELECT m FROM DeviceMeasurementEntity m WHERE m.device.id = :deviceId AND m.createdAt >= :after")
    fun findByDeviceIdAndCreatedAtAfter(deviceId: UUID, after: LocalDateTime): List<DeviceMeasurementEntity>

    @Query("SELECT m FROM DeviceMeasurementEntity m WHERE m.device.greenhouse.id = :greenhouseId AND m.createdAt >= :after")
    fun findByGreenhouseIdAndCreatedAtAfter(greenhouseId: UUID, after: LocalDateTime): List<DeviceMeasurementEntity>

    @Query("SELECT AVG(m.value) FROM DeviceMeasurementEntity m WHERE m.device.id = :deviceId AND m.createdAt >= :after")
    fun findAverageValueByDeviceIdAndPeriod(deviceId: UUID, after: LocalDateTime): Double?
}