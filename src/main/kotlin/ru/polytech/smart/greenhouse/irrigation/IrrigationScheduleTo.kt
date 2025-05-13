package ru.polytech.smart.greenhouse.irrigation

import io.swagger.v3.oas.annotations.media.Schema
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class IrrigationScheduleTo(
    @Schema(description = "Уникальный идентификатор расписания полива")
    val id: UUID? = null,

    @Schema(description = "Идентификатор грядки для полива")
    var bedId: UUID?,

    @Schema(description = "Дни недели для полива")
    val daysOfWeek: Set<DayOfWeek> = emptySet(),

    @Schema(description = "Время начала полива")
    var startTime: LocalTime?,

    @Schema(description = "Время окончания полива")
    var endTime: LocalTime?,

    @Schema(description = "Активно ли расписание")
    var isActive: Boolean?,

    @Schema(description = "Дата создания записи", accessMode = Schema.AccessMode.READ_ONLY)
    var createdAt: LocalDateTime?,

    @Schema(description = "Дата последнего обновления", accessMode = Schema.AccessMode.READ_ONLY)
    var updatedAt: LocalDateTime?
)