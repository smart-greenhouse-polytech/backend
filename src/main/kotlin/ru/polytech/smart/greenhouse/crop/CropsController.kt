package ru.polytech.smart.greenhouse.crop

interface CropsController {
    fun getCrops(): List<CropEntity>
}