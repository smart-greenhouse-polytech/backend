package ru.polytech.smart.greenhouse.bed.impl

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.bed.BedController
import ru.polytech.smart.greenhouse.bed.BedMapper
import ru.polytech.smart.greenhouse.bed.BedRepository
import ru.polytech.smart.greenhouse.bed.BedTo
import java.util.UUID

@RestController
@RequestMapping("/v1/beds")
@CrossOrigin
@RequiredArgsConstructor
class BedControllerImpl(
    private val bedRepository: BedRepository,
    private val bedMapper: BedMapper
) : BedController {

    @Transactional(readOnly = true)
    override fun getAllBeds(): List<BedTo> {
        return bedMapper.toDto(bedRepository.findAll())
    }

    @Transactional(readOnly = true)
    override fun getBedById(id: UUID): ResponseEntity<BedTo> {
        return bedRepository.findById(id)
            .map { ResponseEntity.ok(bedMapper.toDto(it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    override fun createBed(bedTo: BedTo): ResponseEntity<BedTo> = bedTo
        .let(bedMapper::toEntity)
        .let(bedRepository::save)
        .let(bedMapper::toDto)
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }


    @Transactional
    override fun updateBed(id: UUID, bed: BedTo): ResponseEntity<BedTo> {
        return bedRepository.findById(id)
            .map { existingEntity ->
                bedMapper.updateEntityFromDto(existingEntity, bed)
                ResponseEntity.ok(bedMapper.toDto(bedRepository.save(existingEntity)))
            }.orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    override fun deleteBed(id: UUID): ResponseEntity<Void> {
        return if (bedRepository.existsById(id)) {
            bedRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
