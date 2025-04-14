package ru.polytech.smart.greenhouse.crop

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

data class CropTo(
    @Schema(description = "Уникальный идентификатор культуры")
    var id: UUID? = null,

    @Schema(description = "Название культуры", required = true)
    val name: String?,

    @Schema(description = "Требуемое количество воды (л/день)", required = true)
    val waterRequirementLiters: Double?,

    @Schema(description = "Минимальная температура (°C)", required = true)
    val tempMin: Double?,

    @Schema(description = "Максимальная температура (°C)", required = true)
    val tempMax: Double?,

    @Schema(description = "Дата создания записи", accessMode = Schema.AccessMode.READ_ONLY)
    val createdAt: LocalDateTime? = null,

    @Schema(description = "Дата последнего обновления", accessMode = Schema.AccessMode.READ_ONLY)
    val updatedAt: LocalDateTime? = null
)