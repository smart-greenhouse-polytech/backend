package ru.polytech.smart.greenhouse.device

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@Tag(name = "Device Management", description = "API для управления устройствами и получения данных")
@RequestMapping("/v1/devices")
interface DeviceController {

    @Operation(
        summary = "Получить все устройства",
        description = "Возвращает список всех зарегистрированных устройств",
        responses = [
            ApiResponse(responseCode = "200", description = "Успешное получение списка"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    @GetMapping
    fun getAllDevices(): List<DeviceEntity>

    @Operation(
        summary = "Получить устройство по ID",
        description = "Возвращает устройство по указанному идентификатору",
        responses = [
            ApiResponse(responseCode = "200", description = "Устройство найдено"),
            ApiResponse(responseCode = "404", description = "Устройство не найдено")
        ]
    )
    @GetMapping("/{id}")
    fun getDevice(
        @Parameter(description = "ID устройства", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<DeviceEntity>

    @Operation(
        summary = "Получить измерения",
        description = "Возвращает историю измерений за указанный период",
        responses = [
            ApiResponse(responseCode = "200", description = "Измерения получены"),
            ApiResponse(responseCode = "404", description = "Устройство не найдено")
        ]
    )
    @GetMapping("/{id}/measurements")
    fun getMeasurements(
        @Parameter(description = "ID устройства", required = true)
        @PathVariable id: UUID,

        @Parameter(description = "Период в часах", example = "24")
        @RequestParam(defaultValue = "24") hours: Int
    ): List<DeviceMeasurementEntity>
}