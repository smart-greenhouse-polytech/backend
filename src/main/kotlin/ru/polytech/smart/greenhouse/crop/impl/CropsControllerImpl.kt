package ru.polytech.smart.greenhouse.crop.impl

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.crop.CropEntity
import ru.polytech.smart.greenhouse.crop.CropsController
import ru.polytech.smart.greenhouse.crop.CropsRepository
import java.util.UUID

@RestController
@RequestMapping("/v1/crops")
@CrossOrigin
@RequiredArgsConstructor
class CropsControllerImpl(
    private val cropsRepository: CropsRepository
) : CropsController {

    @Transactional(readOnly = true)
    override fun getCrops(): List<CropEntity> {
        return cropsRepository.findAll()
    }

    @Transactional(readOnly = true)
    override fun getCropById(id: UUID): ResponseEntity<CropEntity> {
        return cropsRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    override fun createCrop(crop: CropEntity): ResponseEntity<CropEntity> {
        crop.id = UUID.randomUUID()
        val savedCrop = cropsRepository.save(crop)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCrop)
    }

    @Transactional
    override fun updateCrop(id: UUID, crop: CropEntity): ResponseEntity<CropEntity> {
        return if (cropsRepository.existsById(id)) {
            crop.id = id
            val updatedCrop = cropsRepository.save(crop)
            ResponseEntity.ok(updatedCrop)
        } else {
            ResponseEntity.notFound().build()
        }
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