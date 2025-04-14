package ru.polytech.smart.greenhouse.device

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

data class DeviceMeasurementTo (
    @Id
    @UuidGenerator
    @Schema(
        description = "Уникальный идентификатор измерения"
    )
    var id: UUID,

    @ManyToOne
    @JoinColumn(name = "device_id")
    @Schema(
        description = "Устройство, связанное с измерением",
        required = true
    )
    var deviceId: UUID,

    @Schema(
        description = "Значение измерения",
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