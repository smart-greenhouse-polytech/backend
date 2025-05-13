package ru.polytech.smart.greenhouse.greenhouse.impl

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseSettingController
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseSettingMapper
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseSettingRepository
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseSettingTo
import java.util.UUID

@RestController
class GreenhouseSettingControllerImpl(
    private val settingRepository: GreenhouseSettingRepository,
    private val greenhouseSettingMapper: GreenhouseSettingMapper
) : GreenhouseSettingController {

    override fun createSetting(setting: GreenhouseSettingTo): GreenhouseSettingTo {
        return greenhouseSettingMapper.toDto(settingRepository.save(greenhouseSettingMapper.toEntity(setting)))
    }

    override fun getAllSettings(): List<GreenhouseSettingTo> {
        return greenhouseSettingMapper.toDto(settingRepository.findAll())
    }

    override fun getSetting(id: UUID): ResponseEntity<GreenhouseSettingTo> {
        return settingRepository.findById(id)
            .map { ResponseEntity.ok(greenhouseSettingMapper.toDto(it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    override fun updateSetting(
        id: UUID,
        updatedSetting: GreenhouseSettingTo
    ): ResponseEntity<GreenhouseSettingTo> {
        return settingRepository.findById(id)
            .map { existingEntity ->
                greenhouseSettingMapper.updateSettingFromDto(existingEntity, updatedSetting)
                ResponseEntity.ok(greenhouseSettingMapper.toDto(settingRepository.save(existingEntity)))
            }.orElse(ResponseEntity.notFound().build())
    }

    override fun deleteSetting(id: UUID): ResponseEntity<Void> {
        return if (settingRepository.existsById(id)) {
            settingRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}