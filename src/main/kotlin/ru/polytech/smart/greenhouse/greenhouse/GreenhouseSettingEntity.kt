package ru.polytech.smart.greenhouse.greenhouse

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "greenhouse_setting")
data class GreenhouseSettingEntity(
    @Id
    @UuidGenerator
    var id: UUID,

    @ManyToOne
    @JoinColumn(name = "greenhouse_id")
    var greenhouse: GreenhouseEntity,

    var tempMin: Double,
    var tempMax: Double,
    var humidityMin: Double,
    var humidityMax: Double,
    var lightIntensityMin: Double,
    var lightIntensityMax: Double,

    @CreationTimestamp
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    var updatedAt: LocalDateTime
)