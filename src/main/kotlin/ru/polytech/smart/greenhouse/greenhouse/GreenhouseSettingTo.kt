package ru.polytech.smart.greenhouse.greenhouse

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

data class GreenhouseSettingTo(
    @Schema(
        description = "Уникальный идентификатор настроек теплицы"
    )
    var id: UUID?,

    @Schema(
        description = "Минимальная рекомендованная температура в теплице"
    )
    var tempMin: Double?,
    @Schema(
        description = "Максимальная рекомендованная температура в теплице"
    )
    var tempMax: Double?,
    @Schema(
        description = "Минимальная рекомендованная влажность в теплице"
    )
    var humidityMin: Double?,
    @Schema(
        description = "Максимальная рекомендованная влажность в теплице"
    )
    var humidityMax: Double?,
    @Schema(
        description = "Минимальная рекомендованная освещенность в теплице"
    )
    var lightIntensityMin: Double?,
    @Schema(
        description = "Максимальная рекомендованная освещенность в теплице"
    )
    var lightIntensityMax: Double?,

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