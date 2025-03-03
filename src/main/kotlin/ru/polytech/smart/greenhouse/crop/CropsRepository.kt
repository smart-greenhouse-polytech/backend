package ru.polytech.smart.greenhouse.crop

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CropsRepository: JpaRepository<CropEntity, UUID>