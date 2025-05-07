package ru.polytech.smart.greenhouse.bed

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BedRepository: JpaRepository<BedEntity, UUID>