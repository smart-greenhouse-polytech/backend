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
        responses = [
            ApiResponse(responseCode = "200", description = "Успешное получение списка"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    @GetMapping
    fun getCrops(): List<CropEntity>

    @Operation(
        summary = "Получить культуру по ID",
        description = "Возвращает культуру по указанному идентификатору",
        responses = [
            ApiResponse(responseCode = "200", description = "Культура найдена"),
            ApiResponse(responseCode = "404", description = "Культура не найдена")
        ]
    )
    @GetMapping("/{id}")
    fun getCropById(
        @Parameter(description = "ID культуры", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<CropEntity>

    @Operation(
        summary = "Создать новую культуру",
        description = "Создает новую культуру с указанными параметрами",
        responses = [
            ApiResponse(responseCode = "201", description = "Культура успешно создана"),
            ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
        ]
    )
    @PostMapping
    fun createCrop(
        @Parameter(description = "Данные новой культуры", required = true)
        @RequestBody crop: CropEntity
    ): ResponseEntity<CropEntity>

    @Operation(
        summary = "Обновить культуру",
        description = "Обновляет данные существующей культуры",
        responses = [
            ApiResponse(responseCode = "200", description = "Культура успешно обновлена"),
            ApiResponse(responseCode = "404", description = "Культура не найдена"),
            ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
        ]
    )
    @PutMapping("/{id}")
    fun updateCrop(
        @Parameter(description = "ID культуры", required = true)
        @PathVariable id: UUID,

        @Parameter(description = "Обновленные данные культуры", required = true)
        @RequestBody crop: CropEntity
    ): ResponseEntity<CropEntity>

    @Operation(
        summary = "Удалить культуру",
        description = "Удаляет культуру по указанному идентификатору",
        responses = [
            ApiResponse(responseCode = "204", description = "Культура успешно удалена"),
            ApiResponse(responseCode = "404", description = "Культура не найдена")
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteCrop(
        @Parameter(description = "ID культуры", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<Void>
}