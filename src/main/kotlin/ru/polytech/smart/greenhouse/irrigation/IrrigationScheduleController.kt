package ru.polytech.smart.greenhouse.irrigation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Управление расписанием полива", description = "API для работы с расписанием полива")
interface IrrigationScheduleController {

    @Operation(
        summary = "Создать расписание",
        responses = [
            ApiResponse(responseCode = "200", description = "Успешное создание"),
            ApiResponse(responseCode = "404", description = "Грядка не найдена")
        ]
    )
    @PostMapping
    fun createSchedule(@RequestBody dto: IrrigationScheduleEntity): ResponseEntity<IrrigationScheduleEntity>

    @Operation(summary = "Получить расписание по ID")
    @GetMapping("/{id}")
    fun getSchedule(
        @Parameter(description = "ID расписания")
        @PathVariable id: UUID
    ): ResponseEntity<IrrigationScheduleEntity>

    @Operation(summary = "Получить все расписания")
    @GetMapping
    fun getAllSchedules(): ResponseEntity<List<IrrigationScheduleEntity>>

    @Operation(summary = "Обновить расписание")
    @PutMapping("/{id}")
    fun updateSchedule(
        @Parameter(description = "ID расписания")
        @PathVariable id: UUID,
        @RequestBody dto: IrrigationScheduleEntity
    ): ResponseEntity<IrrigationScheduleEntity>

    @Operation(summary = "Удалить расписание")
    @DeleteMapping("/{id}")
    fun deleteSchedule(
        @Parameter(description = "ID расписания")
        @PathVariable id: UUID
    ): ResponseEntity<Void>

    @Operation(summary = "Переключить активность")
    @PatchMapping("/{id}/toggle")
    fun toggleSchedule(
        @Parameter(description = "ID расписания")
        @PathVariable id: UUID
    ): ResponseEntity<IrrigationScheduleEntity>
}