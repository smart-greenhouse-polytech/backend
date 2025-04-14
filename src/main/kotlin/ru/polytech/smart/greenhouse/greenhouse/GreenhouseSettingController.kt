package ru.polytech.smart.greenhouse.greenhouse

import io.swagger.v3.oas.annotations.Operation
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

@Tag(name = "Greenhouse Settings Management", description = "API для управления настройками теплиц")
@RequestMapping("/v1/greenhouse/settings")
interface GreenhouseSettingController {

    @Operation(summary = "Создать новые настройки")
    @PostMapping
    fun createSetting(@RequestBody setting: GreenhouseSettingTo): GreenhouseSettingTo

    @Operation(summary = "Получить все настройки")
    @GetMapping
    fun getAllSettings(): List<GreenhouseSettingTo>

    @Operation(summary = "Получить настройки по ID")
    @GetMapping("/{id}")
    fun getSetting(@PathVariable id: UUID): ResponseEntity<GreenhouseSettingTo>

    @Operation(summary = "Обновить настройки")
    @PutMapping("/{id}")
    fun updateSetting(
        @PathVariable id: UUID,
        @RequestBody updatedSetting: GreenhouseSettingTo
    ): ResponseEntity<GreenhouseSettingTo>

    @Operation(summary = "Удалить настройки")
    @DeleteMapping("/{id}")
    fun deleteSetting(@PathVariable id: UUID): ResponseEntity<Void>
}