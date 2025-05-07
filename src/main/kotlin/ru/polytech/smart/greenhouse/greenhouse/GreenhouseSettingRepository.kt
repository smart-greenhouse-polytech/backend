package ru.polytech.smart.greenhouse.greenhouse

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GreenhouseSettingRepository : JpaRepository<GreenhouseSettingEntity, UUID>