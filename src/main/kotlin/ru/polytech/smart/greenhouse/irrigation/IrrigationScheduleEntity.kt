package ru.polytech.smart.greenhouse.irrigation

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import ru.polytech.smart.greenhouse.bed.BedEntity
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "irrigation_schedule")
data class IrrigationScheduleEntity(
    @Id
    @UuidGenerator
    var id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "bed_id")
    var bed: BedEntity?,

    var daysOfWeek: String,

    var startTime: LocalTime,

    var requiredVolumeLiters: Double,

    var isActive: Boolean,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)