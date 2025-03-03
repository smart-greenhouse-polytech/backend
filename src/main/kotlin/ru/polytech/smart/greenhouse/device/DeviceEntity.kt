package ru.polytech.smart.greenhouse.device

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import ru.polytech.smart.greenhouse.bed.BedEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseEntity
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "devices")
data class DeviceEntity(
    @Id
    @UuidGenerator
    var id: UUID,

    var name: String,
    var type: String,

    @ManyToOne
    @JoinColumn(name = "greenhouse_id")
    var greenhouse: GreenhouseEntity,

    @Enumerated(EnumType.STRING)
    var status: DeviceStatus = DeviceStatus.INACTIVE,

    @ManyToOne
    @JoinColumn(name = "bed_id")
    var bed: BedEntity?,

    @CreationTimestamp
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    var updatedAt: LocalDateTime
)