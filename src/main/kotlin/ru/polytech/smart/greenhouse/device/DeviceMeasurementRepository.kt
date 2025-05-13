package ru.polytech.smart.greenhouse.device

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface DeviceMeasurementRepository : JpaRepository<DeviceMeasurementEntity, UUID> {

    @Query("SELECT m FROM DeviceMeasurementEntity m WHERE m.device.id = :deviceId AND m.createdAt >= :after")
    fun findByDeviceIdAndCreatedAtAfter(deviceId: UUID, after: LocalDateTime): List<DeviceMeasurementEntity>

    @Query("""
        SELECT m FROM DeviceMeasurementEntity m
        WHERE m.createdAt = (
            SELECT MAX(m2.createdAt) FROM DeviceMeasurementEntity m2
            WHERE m2.device = m.device
        )
        AND m.device.status = 'ACTIVE'
    """)
    fun findLatestMeasurementsOfActiveDevices(): List<DeviceMeasurementEntity>
}