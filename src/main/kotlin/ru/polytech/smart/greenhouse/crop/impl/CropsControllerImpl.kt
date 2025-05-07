package ru.polytech.smart.greenhouse.crop.impl

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.crop.CropTo
import ru.polytech.smart.greenhouse.crop.CropMapper
import ru.polytech.smart.greenhouse.crop.CropsController
import ru.polytech.smart.greenhouse.crop.CropsRepository
import java.util.UUID

@RestController
@RequestMapping("/api/v1/crops")
@CrossOrigin
@RequiredArgsConstructor
class CropsControllerImpl(
    private val cropsRepository: CropsRepository,
    private val cropsMapper: CropMapper,
) : CropsController {

    @Transactional(readOnly = true)
    override fun getCrops(): List<CropTo> {
        return cropsMapper.toDto(cropsRepository.findAll())
    }

    @Transactional(readOnly = true)
    override fun getCropById(id: UUID): ResponseEntity<CropTo> {
        return cropsRepository.findById(id)
            .map { ResponseEntity.ok(cropsMapper.toDto(it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    override fun createCrop(crop: CropTo): ResponseEntity<CropTo> {
        val savedCrop = cropsRepository.save(cropsMapper.toEntity(crop))
        return ResponseEntity.status(HttpStatus.CREATED).body(cropsMapper.toDto(savedCrop))
    }

    @Transactional
    override fun updateCrop(id: UUID, crop: CropTo): ResponseEntity<CropTo> {
        return cropsRepository.findById(id)
            .map { existingEntity ->
                cropsMapper.updateEntityFromDto(existingEntity, crop)
                ResponseEntity.ok(cropsMapper.toDto(cropsRepository.save(existingEntity)))
            }.orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    override fun deleteCrop(id: UUID): ResponseEntity<Void> {
        return if (cropsRepository.existsById(id)) {
            cropsRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}