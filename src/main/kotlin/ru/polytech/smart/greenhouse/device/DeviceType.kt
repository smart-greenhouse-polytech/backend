package ru.polytech.smart.greenhouse.device

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Типы устройств")
enum class DeviceType(val topicName: String) {
    // Датчики
    TEMPERATURE_GROUND("temperature_ground"),
    TEMPERATURE_MIDDLE("temperature_middle"),
    TEMPERATURE_CEIL("temperature_ceil"),
    HUMIDITY_AIR("humidity_air"),
    HUMIDITY_GROUND("humidity_ground"),
    PRESSURE_AIR("pressure_air"),
    CAMERA("camera"),

    // Устройства
    HEATING("heating"),
    WINDOW("windows"),
    WATER_VALVE("valve_water"),
    FAN("fan_cooling");

    companion object {
        fun fromTopic(topic: String): DeviceType? {
            return values().firstOrNull { it.topicName == topic }
        }
    }
}