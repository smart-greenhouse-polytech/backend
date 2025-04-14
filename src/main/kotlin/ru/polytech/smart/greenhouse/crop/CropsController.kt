package ru.polytech.smart.greenhouse.crop

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
import java.util.UUID

@Tag(name = "Crops Management", description = "API для управления культурами в теплице")
interface CropsController {

    @Operation(
        summary = "Получить все культуры",
        description = "Возвращает список всех культур",
    )
    @GetMapping
    fun getCrops(): List<CropTo>

    @Operation(
        summary = "Получить культуру по ID",
        description = "Возвращает культуру по указанному идентификатору",
    )
    @GetMapping("/{id}")
    fun getCropById(
        @Parameter(description = "ID культуры", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<CropTo>

    @Operation(
        summary = "Создать новую культуру",
        description = "Создает новую культуру с указанными параметрами",
    )
    @PostMapping
    fun createCrop(
        @Parameter(description = "Данные новой культуры", required = true)
        @RequestBody crop: CropTo
    ): ResponseEntity<CropTo>

    @Operation(
        summary = "Обновить культуру",
        description = "Обновляет данные существующей культуры",
    )
    @PutMapping("/{id}")
    fun updateCrop(
        @Parameter(description = "ID культуры", required = true)
        @PathVariable id: UUID,

        @Parameter(description = "Обновленные данные культуры", required = true)
        @RequestBody crop: CropTo
    ): ResponseEntity<CropTo>

    @Operation(
        summary = "Удалить культуру",
        description = "Удаляет культуру по указанному идентификатору",
    )
    @DeleteMapping("/{id}")
    fun deleteCrop(
        @Parameter(description = "ID культуры", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<Void>
}