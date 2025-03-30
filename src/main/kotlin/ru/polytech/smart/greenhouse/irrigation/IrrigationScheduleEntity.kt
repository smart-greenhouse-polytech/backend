package ru.polytech.smart.greenhouse.irrigation

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import ru.polytech.smart.greenhouse.bed.BedEntity
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "irrigation_schedule")
data class IrrigationScheduleEntity(
    @Id
    @UuidGenerator
    @Schema(description = "Уникальный идентификатор расписания полива")
    var id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "bed_id")
    @Schema(description = "Грядка для полива")
    var bed: BedEntity? = null,

    @Schema(description = "Дни недели для полива (через запятую, 1-7 где 1-понедельник)")
    var daysOfWeek: String,

    @Schema(description = "Время начала полива")
    var startTime: LocalTime,

    @Schema(description = "Требуемый объем воды в литрах")
    var requiredVolumeLiters: Double,

    @Schema(description = "Активно ли расписание")
    var isActive: Boolean = true,

    @CreationTimestamp
    @Schema(description = "Дата создания записи", accessMode = Schema.AccessMode.READ_ONLY)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    @Schema(description = "Дата последнего обновления", accessMode = Schema.AccessMode.READ_ONLY)
    var updatedAt: LocalDateTime? = null
)