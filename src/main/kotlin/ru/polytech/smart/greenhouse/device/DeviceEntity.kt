package ru.polytech.smart.greenhouse.device

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
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
    var id: UUID = UUID.randomUUID(),

    var name: String,

    val deviceId: String? = null,

    val macAddress: String? = null,

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

    @Embedded
    val credentials: Credentials? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)

@Embeddable
data class Credentials(
    @Column(name = "mqtt_username")
    val username: String,

    @Column(name = "mqtt_password")
    val password: String
)