package ru.polytech.smart.greenhouse.device

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Типы устройств")
enum class DeviceType {
    @Schema(description = "Термометр")
    THERMOMETER,

    @Schema(description = "Датчик влажности")
    HUMIDITY_SENSOR,

    @Schema(description = "Датчик освещенности")
    LIGHT_SENSOR,

    @Schema(description = "Окно")
    WINDOW,

    @Schema(description = "Система полива")
    IRRIGATOR
}