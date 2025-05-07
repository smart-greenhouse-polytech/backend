package ru.polytech.smart.greenhouse.device

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.polytech.smart.greenhouse.bed.BedTo
import java.util.UUID

@Tag(name = "Device Management", description = "API для управления устройствами и получения данных")
@RequestMapping("/api/v1/devices")
interface DeviceController {

    @Operation(
        summary = "Получить все устройства",
        description = "Возвращает список всех зарегистрированных устройств",
    )
    @GetMapping
    fun getAllDevices(): List<DeviceTo>

    @Operation(
        summary = "Зарегистрировать новое устройство",
        description = "Регистрирует новое устройство в системе",
    )
    @PostMapping
    fun registerDevice(
        @Parameter(description = "ID устройства", required = true)
        @RequestBody deviceTo: DeviceTo
    ): ResponseEntity<DeviceTo>

    @Operation(
        summary = "Получить устройство по ID",
        description = "Возвращает устройство по указанному идентификатору",
    )
    @GetMapping("/{id}")
    fun getDevice(
        @Parameter(description = "ID устройства", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<DeviceTo>

    @Operation(
        summary = "Изменить устройство по указанному ID",
        description = "Изменяет устройство по указанному ID",
    )
    @PutMapping("/{id}")
    fun updateDevice(
        @Parameter(description = "ID устройства", required = true)
        @PathVariable id: UUID,

        @Parameter(description = "Новые данные для устройства", required = true)
        @RequestBody deviceTo: DeviceTo
    ): ResponseEntity<DeviceTo>

    @Operation(
        summary = "Удалить устройство по ID",
        description = "Удаляет устройство по ID",
    )
    @DeleteMapping("/{id}")
    fun deleteDevice(
        @Parameter(description = "ID устройства", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<Void>

    @Operation(
        summary = "Получить измерения устройства",
        description = "Возвращает историю измерений для отдельного устройства за указанный период",
    )
    @GetMapping("/{id}/measurements")
    fun getMeasurements(
        @Parameter(description = "ID устройства", required = true)
        @PathVariable id: UUID,

        @Parameter(description = "Период в часах", example = "24")
        @RequestParam(defaultValue = "24") hours: Int
    ): List<DeviceMeasurementTo>

    @Operation(
        summary = "Получить текущие измерения в теплице",
        description = "Возвращает среднее значение всех измерений в данный момент",
        tags = ["Experimental"]
    )
    @GetMapping("/measurements")
    fun getCurrentMeasurements(): List<DeviceMeasurementTo>

    @Operation(
        summary = "Отправить команду устройству",
        description = "Позволяет вручную отправить команду включения/выключения исполнительному устройству.",
    )
    @PostMapping("{id}/control")
    fun controlDevice(
        @Parameter(description = "ID устройства", required = true)
        @PathVariable id: UUID,
        @Parameter(description = "Желаемое состояние устройства", required = true)
        @RequestParam command: String,
    ): ResponseEntity<String>
}