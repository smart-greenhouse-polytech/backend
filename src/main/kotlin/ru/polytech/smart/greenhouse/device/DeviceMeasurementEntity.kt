package ru.polytech.smart.greenhouse.device

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "device_measurement")
data class DeviceMeasurementEntity(
    @Id
    @UuidGenerator
    var id: UUID,

    @ManyToOne
    @JoinColumn(name = "device_id")
    var device: DeviceEntity,

    var value: Double,

    @CreationTimestamp
    var createdAt: LocalDateTime
)