package ru.polytech.smart.greenhouse.crop

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "crops")
@DynamicInsert
@DynamicUpdate
data class CropEntity(
    @Id
    @UuidGenerator
    val id: UUID? = null,

    var name: String,

    var waterRequirementLiters: Double,

    var tempMin: Double,

    var tempMax: Double,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
)