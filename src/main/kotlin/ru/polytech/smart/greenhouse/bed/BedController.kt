package ru.polytech.smart.greenhouse.bed

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
import java.util.UUID

@Tag(name = "Beds Management", description = "API для управления грядками в теплице")
interface BedController {

    @Operation(
        summary = "Получить все грядки",
        description = "Возвращает список всех грядок",
    )
    @GetMapping
    fun getAllBeds(): List<BedTo>

    @Operation(
        summary = "Получить грядку по ID",
        description = "Возвращает грядку по указанному идентификатору",
    )
    @GetMapping("/{id}")
    fun getBedById(
        @Parameter(description = "ID грядки", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<BedTo>

    @Operation(
        summary = "Создать новую грядку",
        description = "Создает новую грядку с указанными параметрами",
    )
    @PostMapping
    fun createBed(
        @Parameter(description = "Данные новой грядки", required = true)
        @RequestBody bedTo: BedTo
    ): ResponseEntity<BedTo>

    @Operation(
        summary = "Обновить грядку",
        description = "Обновляет данные существующей грядки",
    )
    @PutMapping("/{id}")
    fun updateBed(
        @Parameter(description = "ID грядки", required = true)
        @PathVariable id: UUID,

        @Parameter(description = "Обновленные данные грядки", required = true)
        @RequestBody bed: BedTo
    ): ResponseEntity<BedTo>

    @Operation(
        summary = "Удалить грядку",
        description = "Удаляет грядку по указанному идентификатору",
    )
    @DeleteMapping("/{id}")
    fun deleteBed(
        @Parameter(description = "ID грядки", required = true)
        @PathVariable id: UUID
    ): ResponseEntity<Void>
}