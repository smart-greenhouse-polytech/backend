package ru.polytech.smart.greenhouse.greenhouse

import jakarta.persistence.Entity
import jakarta.persistence.Id
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
    val id: UUID? = null,

    var tempMin: Double,
    var tempMax: Double,
    var humidityMin: Double,
    var humidityMax: Double,
    var lightIntensityMin: Double,
    var lightIntensityMax: Double,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now()
)