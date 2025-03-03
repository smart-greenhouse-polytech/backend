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
import java.util.UUID

@Entity
@Table(name = "irrigation_schedule")
data class IrrigationScheduleEntity(
    @Id
    @UuidGenerator
    var id: UUID,

    @ManyToOne
    @JoinColumn(name = "bed_id")
    var bed: BedEntity,

    var daysOfWeek: String,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,

    @CreationTimestamp
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    var updatedAt: LocalDateTime
)