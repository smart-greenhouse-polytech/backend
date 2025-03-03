package ru.polytech.smart.greenhouse.crop.impl

import lombok.RequiredArgsConstructor
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.crop.CropEntity
import ru.polytech.smart.greenhouse.crop.CropsController
import ru.polytech.smart.greenhouse.crop.CropsRepository

@RestController
@RequestMapping("/v1/crops")
@CrossOrigin
@RequiredArgsConstructor
class CropsControllerImpl(
    private val cropsRepository: CropsRepository
) : CropsController {

    @GetMapping
    @Transactional
    override fun getCrops(): List<CropEntity> {
        return cropsRepository.findAll()
    }
}