
package ru.polytech.smart.greenhouse.irrigation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IrrigationScheduleRepository : JpaRepository<IrrigationScheduleEntity, UUID> {
    fun findByBedId(bedId: UUID): List<IrrigationScheduleEntity>
    fun findByIsActive(isActive: Boolean): List<IrrigationScheduleEntity>
    fun findByBedIdAndIsActive(bedId: UUID, isActive: Boolean): List<IrrigationScheduleEntity>
}