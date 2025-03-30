package ru.polytech.smart.greenhouse.bed

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BedRepository: JpaRepository<BedEntity, UUID>