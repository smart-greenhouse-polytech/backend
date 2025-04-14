import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseMapper
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseOwnerEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseRepository
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseTo
import java.util.UUID

@RestController
class GreenhouseControllerImpl(
    private val greenhouseRepository: GreenhouseRepository,
    private val greenhouseMapper: GreenhouseMapper,
) : GreenhouseController {

    override fun createGreenhouse(greenhouse: GreenhouseTo): GreenhouseTo {
        greenhouse.id = UUID.randomUUID()
        return greenhouseMapper.toDto(greenhouseRepository.save(greenhouseMapper.toEntity(greenhouse)))
    }

    override fun getAllGreenhouses(): List<GreenhouseTo> {
        return greenhouseMapper.toDto(greenhouseRepository.findAll())
    }

    override fun getGreenhouse(id: UUID): ResponseEntity<GreenhouseTo> {
        return greenhouseRepository.findById(id)
            .map { ResponseEntity.ok(greenhouseMapper.toDto(it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    override fun updateGreenhouse(
        id: UUID,
        updatedGreenhouse: GreenhouseTo
    ): ResponseEntity<GreenhouseTo> {
        return greenhouseRepository.findById(id)
            .map { existingEntity ->
                greenhouseMapper.updateEntityFromDto(existingEntity, updatedGreenhouse)
                ResponseEntity.ok(greenhouseMapper.toDto(greenhouseRepository.save(existingEntity)))
            }.orElse(ResponseEntity.notFound().build())
    }

    override fun deleteGreenhouse(id: UUID): ResponseEntity<Void> {
        return if (greenhouseRepository.existsById(id)) {
            greenhouseRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    override fun addOwner(
        greenhouseId: UUID,
        owner: GreenhouseOwnerEntity
    ): ResponseEntity<GreenhouseOwnerEntity?> {
        return ResponseEntity.ok(null)
    }

    override fun getGreenhousesByOwner(ownerId: UUID): List<GreenhouseTo> {
        return ArrayList()
    }
}