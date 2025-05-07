package ru.polytech.smart.greenhouse.device

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import ru.polytech.smart.greenhouse.bed.BedEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseEntity
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "devices")
data class DeviceEntity(
    @Id
    @UuidGenerator
    var id: UUID = UUID.randomUUID(),

    var name: String,

    @Enumerated(EnumType.STRING)
    var type: DeviceType,

    @ManyToOne
    @JoinColumn(name = "greenhouse_id")
    var greenhouse: GreenhouseEntity? = null,

    @Enumerated(EnumType.STRING)
    var status: DeviceStatus = DeviceStatus.INACTIVE,

    @ManyToOne
    @JoinColumn(name = "bed_id")
    var bed: BedEntity? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)