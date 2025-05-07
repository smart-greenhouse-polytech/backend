package ru.polytech.smart.greenhouse.device

import java.time.LocalDateTime

data class DeviceIdentifier(
    val deviceType: DeviceType,
    val deviceName: String
)

data class SensorReading(
    val deviceName: String,
    val value: Double,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

data class DeviceCommand(
    val deviceName: String,
    val command: String
)