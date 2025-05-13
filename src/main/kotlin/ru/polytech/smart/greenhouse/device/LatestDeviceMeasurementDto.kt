package ru.polytech.smart.greenhouse.device

import java.time.LocalDateTime
import java.util.*

data class LatestDeviceMeasurementDto(
    val deviceId: UUID,
    val deviceName: String,
    val deviceType: DeviceType,
    val value: Double,
    val measuredAt: LocalDateTime?
)
