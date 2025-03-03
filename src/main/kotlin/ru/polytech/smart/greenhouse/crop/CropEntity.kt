package ru.polytech.smart.greenhouse.crop

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "crops")
data class CropEntity(
    @Id
    @UuidGenerator
    var id: UUID,

    var name: String,
    var waterRequirement: Double,
    var tempMin: Double,
    var tempMax: Double,

    @CreationTimestamp
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    var updatedAt: LocalDateTime
)
