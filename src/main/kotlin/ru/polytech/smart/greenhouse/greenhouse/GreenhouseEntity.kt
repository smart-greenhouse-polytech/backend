package ru.polytech.smart.greenhouse.greenhouse

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "greenhouses")
data class GreenhouseEntity(
    @Id
    @UuidGenerator
    var id: UUID = UUID.randomUUID(),

    var name: String,

    var location: String?,

    @OneToOne
    @JoinColumn(name = "greenhouse_setting_id")
    var greenhouseSetting: GreenhouseSettingEntity?,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now()
)