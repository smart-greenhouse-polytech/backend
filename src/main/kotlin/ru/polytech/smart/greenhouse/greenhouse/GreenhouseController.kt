import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseOwnerEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseSettingEntity
import java.util.*

@Tag(name = "Greenhouse Management", description = "API для управления теплицами")
@RequestMapping("/v1/greenhouses")
interface GreenhouseController {

    @Operation(summary = "Создать новую теплицу")
    @PostMapping
    fun createGreenhouse(@RequestBody greenhouse: GreenhouseEntity): GreenhouseEntity

    @Operation(summary = "Получить список всех теплиц")
    @GetMapping
    fun getAllGreenhouses(): List<GreenhouseEntity>

    @Operation(summary = "Получить теплицу по ID")
    @GetMapping("/{id}")
    fun getGreenhouse(@PathVariable id: UUID): ResponseEntity<GreenhouseEntity>

    @Operation(summary = "Обновить данные теплицы")
    @PutMapping("/{id}")
    fun updateGreenhouse(
        @PathVariable id: UUID,
        @RequestBody updatedGreenhouse: GreenhouseEntity
    ): ResponseEntity<GreenhouseEntity>

    @Operation(summary = "Удалить теплицу")
    @DeleteMapping("/{id}")
    fun deleteGreenhouse(@PathVariable id: UUID): ResponseEntity<Void>

    @Operation(summary = "Добавить владельца теплицы")
    @PostMapping("/{greenhouseId}/owners")
    fun addOwner(
        @PathVariable greenhouseId: UUID,
        @RequestBody owner: GreenhouseOwnerEntity
    ): ResponseEntity<GreenhouseOwnerEntity>

    @Operation(summary = "Получить всех владельцев теплицы")
    @GetMapping("/{greenhouseId}/owners")
    fun getOwners(@PathVariable greenhouseId: UUID): List<GreenhouseOwnerEntity>

    @Operation(summary = "Получить все теплицы по владельцу")
    @GetMapping("/owners/{ownerId}")
    fun getGreenhousesByOwner(@PathVariable ownerId: UUID): List<GreenhouseEntity>

    @Operation(summary = "Установить настройки теплицы")
    @PostMapping("/{greenhouseId}/settings")
    fun setSettings(
        @PathVariable greenhouseId: UUID,
        @RequestBody settings: GreenhouseSettingEntity
    ): ResponseEntity<GreenhouseSettingEntity>
}