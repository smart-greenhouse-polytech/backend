package ru.polytech.smart.greenhouse.device

import io.swagger.v3.oas.annotations.media.Schema
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
    @Schema(
        description = "Уникальный идентификатор измерения",
        example = "550e8400-e29b-41d4-a716-446655440001"
    )
    var id: UUID,

    @ManyToOne
    @JoinColumn(name = "device_id")
    @Schema(
        description = "Устройство, связанное с измерением",
        required = true
    )
    var device: DeviceEntity,

    @Schema(
        description = "Значение измерения",
        example = "25.5",
        required = true
    )
    var value: Double,

    @CreationTimestamp
    @Schema(
        description = "Дата и время измерения",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    var createdAt: LocalDateTime
)