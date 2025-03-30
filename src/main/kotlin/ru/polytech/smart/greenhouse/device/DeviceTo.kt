package ru.polytech.smart.greenhouse.device

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Embedded
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import ru.polytech.smart.greenhouse.bed.BedEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseEntity
import java.time.LocalDateTime
import java.util.UUID

data class DeviceTo(
    @Id
    @UuidGenerator
    @Schema(
        description = "Уникальный идентификатор устройства"
    )
    var id: UUID,

    @Schema(
        description = "Название устройства",
        required = true
    )
    var name: String,

    @Schema(
        description = "Уникальный идентификатр устройства"
    )
    val deviceId: String,

    @Schema(
        description = "Mac адресс устройства"
    )
    val macAddress: String,

    @Enumerated(EnumType.STRING)
    @Schema(
        description = "Тип устройства",
        required = true
    )
    var type: DeviceType,

    @ManyToOne
    @JoinColumn(name = "greenhouse_id")
    @Schema(
        description = "Теплица, к которой привязано устройство",
        required = true
    )
    var greenhouse: GreenhouseEntity,

    @Enumerated(EnumType.STRING)
    @Schema(
        description = "Статус устройства",
        defaultValue = "INACTIVE"
    )
    var status: DeviceStatus = DeviceStatus.INACTIVE,

    @ManyToOne
    @JoinColumn(name = "bed_id")
    @Schema(
        description = "Грядка, к которой привязано устройство",
        nullable = true
    )
    var bed: BedEntity?,

    @Embedded
    @Schema(description = "Даггые")
    val credentials: Credentials,

    @CreationTimestamp
    @Schema(
        description = "Дата создания записи",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    @Schema(
        description = "Дата последнего обновления",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    var updatedAt: LocalDateTime
)