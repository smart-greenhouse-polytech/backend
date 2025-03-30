package ru.polytech.smart.greenhouse.bed

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

data class BedTo(
    @Schema(description = "Уникальный идентификатор грядки")
    var id: UUID,

    @Schema(description = "ID теплицы", required = true)
    val greenhouseId: UUID?,

    @Schema(description = "Название грядки", example = "Грядка №1", required = true)
    val name: String,

    @Schema(description = "ID культуры", nullable = true)
    val cropId: UUID?,

    @Schema(description = "Список ID устройств", nullable = true)
    val deviceIds: List<UUID>?,

    @Schema(description = "Дата создания записи", accessMode = Schema.AccessMode.READ_ONLY)
    val createdAt: LocalDateTime?,

    @Schema(description = "Дата последнего обновления", accessMode = Schema.AccessMode.READ_ONLY)
    val updatedAt: LocalDateTime?
)