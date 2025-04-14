package ru.polytech.smart.greenhouse.irrigation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Tag(name = "Управление расписанием полива", description = "API для работы с расписанием полива")
@RequestMapping("/v1/irrigation")
interface IrrigationScheduleController {

    @Operation(summary = "Создать расписание",)
    @PostMapping
    fun createSchedule(@RequestBody dto: IrrigationScheduleTo): ResponseEntity<IrrigationScheduleTo>

    @Operation(summary = "Получить расписание по ID")
    @GetMapping("/{id}")
    fun getSchedule(
        @Parameter(description = "ID расписания")
        @PathVariable id: UUID
    ): ResponseEntity<IrrigationScheduleTo>

    @Operation(summary = "Получить все расписания")
    @GetMapping
    fun getAllSchedules(): List<IrrigationScheduleTo>

    @Operation(summary = "Обновить расписание")
    @PutMapping("/{id}")
    fun updateSchedule(
        @Parameter(description = "ID расписания")
        @PathVariable id: UUID,
        @RequestBody dto: IrrigationScheduleTo
    ): ResponseEntity<IrrigationScheduleTo>

    @Operation(summary = "Удалить расписание")
    @DeleteMapping("/{id}")
    fun deleteSchedule(
        @Parameter(description = "ID расписания")
        @PathVariable id: UUID
    ): ResponseEntity<Void>
}