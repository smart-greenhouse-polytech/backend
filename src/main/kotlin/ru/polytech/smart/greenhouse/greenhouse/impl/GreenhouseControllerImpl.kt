import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseOwnerEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseOwnerRepository
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseRepository
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseSettingEntity
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseSettingRepository
import java.util.*

@RestController
class GreenhouseControllerImpl(
    private val greenhouseRepository: GreenhouseRepository,
    private val ownerRepository: GreenhouseOwnerRepository,
    private val settingRepository: GreenhouseSettingRepository
) : GreenhouseController {

    override fun createGreenhouse(greenhouse: GreenhouseEntity): GreenhouseEntity {
        greenhouse.id = UUID.randomUUID()
        return greenhouseRepository.save(greenhouse)
    }

    override fun getAllGreenhouses(): List<GreenhouseEntity> {
        return greenhouseRepository.findAll()
    }

    override fun getGreenhouse(id: UUID): ResponseEntity<GreenhouseEntity> {
        return greenhouseRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    override fun updateGreenhouse(
        id: UUID,
        updatedGreenhouse: GreenhouseEntity
    ): ResponseEntity<GreenhouseEntity> {
        return if (greenhouseRepository.existsById(id)) {
            updatedGreenhouse.id = id
            ResponseEntity.ok(greenhouseRepository.save(updatedGreenhouse))
        } else {
            ResponseEntity.notFound().build()
        }
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
    ): ResponseEntity<GreenhouseOwnerEntity> {
        if (!greenhouseRepository.existsById(greenhouseId)) {
            return ResponseEntity.notFound().build()
        }
        owner.id = UUID.randomUUID()
        owner.greenhouse = greenhouseRepository.getReferenceById(greenhouseId)
        return ResponseEntity.ok(ownerRepository.save(owner))
    }

    override fun getOwners(greenhouseId: UUID): List<GreenhouseOwnerEntity> {
        return ownerRepository.findByGreenhouseId(greenhouseId)
    }

    override fun getGreenhousesByOwner(ownerId: UUID): List<GreenhouseEntity> {
        val ownerRelations = ownerRepository.findByUserId(ownerId)
        return ownerRelations.map { it.greenhouse }
    }

    override fun setSettings(
        greenhouseId: UUID,
        settings: GreenhouseSettingEntity
    ): ResponseEntity<GreenhouseSettingEntity> {
        if (!greenhouseRepository.existsById(greenhouseId)) {
            return ResponseEntity.notFound().build()
        }
        settings.id = UUID.randomUUID()
        settings.greenhouse = greenhouseRepository.getReferenceById(greenhouseId)
        return ResponseEntity.ok(settingRepository.save(settings))
    }
}