package ru.polytech.smart.greenhouse.greenhouse

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

data class GreenhouseTo(
    @Schema(
        description = "Уникальный идентификатор теплицы"
    )
    val id: UUID? = null,

    @Schema(
        description = "Уникальное имя теплицы"
    )
    var name: String?,

    @Schema(
        description = "Локация теплицы"
    )
    var location: String?,

    @Schema(
        description = "Уникальный идентификатор настроек теплицы"
    )
    var greenhouseSettingId: UUID?,

    @Schema(
        description = "Дата создания записи",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    var createdAt: LocalDateTime?,
    @Schema(
        description = "Дата последнего обновления",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    var updatedAt: LocalDateTime?
)