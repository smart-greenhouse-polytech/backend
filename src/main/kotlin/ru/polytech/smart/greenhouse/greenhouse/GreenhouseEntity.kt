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
@Table(name = "greenhouses")
data class GreenhouseEntity(
    @Id
    @UuidGenerator
    var id: UUID,

    var name: String,

    var location: String?,

    @CreationTimestamp
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    var updatedAt: LocalDateTime
)