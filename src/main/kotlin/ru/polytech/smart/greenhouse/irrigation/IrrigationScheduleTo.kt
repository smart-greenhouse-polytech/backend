package ru.polytech.smart.greenhouse.irrigation

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class IrrigationScheduleTo(
    @Schema(description = "Уникальный идентификатор расписания полива")
    var id: UUID?,

    @Schema(description = "Идентификатор грядки для полива")
    var bedId: UUID?,

    @Schema(description = "Дни недели для полива (через запятую, 1-7 где 1-понедельник)")
    var daysOfWeek: String?,

    @Schema(description = "Время начала полива")
    var startTime: LocalTime?,

    @Schema(description = "Требуемый объем воды в литрах")
    var requiredVolumeLiters: Double?,

    @Schema(description = "Активно ли расписание")
    var isActive: Boolean?,

    @Schema(description = "Дата создания записи", accessMode = Schema.AccessMode.READ_ONLY)
    var createdAt: LocalDateTime?,

    @Schema(description = "Дата последнего обновления", accessMode = Schema.AccessMode.READ_ONLY)
    var updatedAt: LocalDateTime?
)