package ru.polytech.smart.greenhouse.irrigation

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import org.hibernate.type.SqlTypes
import ru.polytech.smart.greenhouse.bed.BedEntity
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "irrigation_schedule")
data class IrrigationScheduleEntity(
    @Id
    @UuidGenerator
    var id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "bed_id")
    var bed: BedEntity?,

    @Column(name = "days_of_week", columnDefinition = "day_of_week[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    var daysOfWeek: Set<DayOfWeek> = emptySet(),

    @Column(name = "start_time")
    var startTime: LocalTime,

    @Column(name = "end_time")
    var endTime: LocalTime,

    @Column(name = "is_active")
    var isActive: Boolean,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
