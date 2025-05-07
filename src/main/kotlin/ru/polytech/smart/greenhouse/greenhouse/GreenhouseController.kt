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
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseOwnerEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseTo
import java.util.UUID

@Tag(name = "Greenhouse Management", description = "API для управления теплицами")
@RequestMapping("/api/v1/greenhouses")
interface GreenhouseController {

    @Operation(summary = "Создать новую теплицу")
    @PostMapping
    fun createGreenhouse(@RequestBody greenhouse: GreenhouseTo): GreenhouseTo

    @Operation(summary = "Получить список всех теплиц")
    @GetMapping
    fun getAllGreenhouses(): List<GreenhouseTo>

    @Operation(summary = "Получить теплицу по ID")
    @GetMapping("/{id}")
    fun getGreenhouse(@PathVariable id: UUID): ResponseEntity<GreenhouseTo>

    @Operation(summary = "Обновить данные теплицы")
    @PutMapping("/{id}")
    fun updateGreenhouse(
        @PathVariable id: UUID,
        @RequestBody updatedGreenhouse: GreenhouseTo
    ): ResponseEntity<GreenhouseTo>

    @Operation(summary = "Удалить теплицу")
    @DeleteMapping("/{id}")
    fun deleteGreenhouse(@PathVariable id: UUID): ResponseEntity<Void>

    @Operation(
        summary = "Добавить владельца теплицы",
        tags = ["Experimental"]
    )
    @PostMapping("/{greenhouseId}/owners")
    fun addOwner(
        @PathVariable greenhouseId: UUID,
        @RequestBody owner: GreenhouseOwnerEntity
    ): ResponseEntity<GreenhouseOwnerEntity?>

    @Operation(
        summary = "Получить все теплицы по владельцу",
        tags = ["Experimental"]
    )
    @GetMapping("/owners/{ownerId}")
    fun getGreenhousesByOwner(@PathVariable ownerId: UUID): List<GreenhouseTo>
}