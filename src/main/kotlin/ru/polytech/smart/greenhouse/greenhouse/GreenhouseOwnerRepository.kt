package ru.polytech.smart.greenhouse.greenhouse

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GreenhouseOwnerRepository : JpaRepository<GreenhouseOwnerEntity, UUID> {
    fun findByGreenhouseId(greenhouseId: UUID): List<GreenhouseOwnerEntity>
    fun findByUserId(userId: UUID): List<GreenhouseOwnerEntity>
}