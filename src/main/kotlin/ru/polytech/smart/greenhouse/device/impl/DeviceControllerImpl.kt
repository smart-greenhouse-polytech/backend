package ru.polytech.smart.greenhouse.device.impl

import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.device.DeviceController
import ru.polytech.smart.greenhouse.device.DeviceMapper
import ru.polytech.smart.greenhouse.device.DeviceMeasurementMapper
import ru.polytech.smart.greenhouse.device.DeviceMeasurementRepository
import ru.polytech.smart.greenhouse.device.DeviceMeasurementTo
import ru.polytech.smart.greenhouse.device.DeviceRepository
import ru.polytech.smart.greenhouse.device.DeviceTo
import ru.polytech.smart.greenhouse.greenhouse.GreenhouseTo
import java.time.LocalDateTime
import java.util.UUID

@RestController
class DeviceControllerImpl(
    private val deviceRepository: DeviceRepository,
    private val measurementRepository: DeviceMeasurementRepository,
    private val deviceMapper: DeviceMapper,
    private val deviceMeasurementMapper: DeviceMeasurementMapper,
) : DeviceController {

    @Transactional(readOnly = true)
    override fun getAllDevices(): List<DeviceTo> {
        return deviceMapper.toDto(deviceRepository.findAll())
    }

    @Transactional
    override fun registerDevice(deviceTo: DeviceTo): ResponseEntity<DeviceTo> {
        val entity = deviceMapper.toEntity(deviceTo)
        val savedDevice = deviceRepository.save(entity)
        return ResponseEntity.ok(deviceMapper.toDto(savedDevice))
    }

    @Transactional(readOnly = true)
    override fun getDevice(id: UUID): ResponseEntity<DeviceTo> {
        return deviceRepository.findById(id)
            .map { ResponseEntity.ok(deviceMapper.toDto(it)) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    override fun updateDevice(id: UUID, deviceTo: DeviceTo): ResponseEntity<DeviceTo> {
        return deviceRepository.findById(id)
            .map { existingEntity ->
                deviceMapper.updateEntityFromDto(existingEntity, deviceTo)
                ResponseEntity.ok(deviceMapper.toDto(deviceRepository.save(existingEntity)))
            }.orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    override fun deleteDevice(id: UUID): ResponseEntity<Void> {
        return if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Transactional(readOnly = true)
    override fun getMeasurements(id: UUID, hours: Int): List<DeviceMeasurementTo> {
        val cutoff = LocalDateTime.now().minusHours(hours.toLong())
        return deviceMeasurementMapper.toDto(measurementRepository.findByDeviceIdAndCreatedAtAfter(id, cutoff))
    }

    @Transactional(readOnly = true)
    override fun getCurrentMeasurements(): List<DeviceMeasurementTo> {
        return deviceMeasurementMapper.toDto(measurementRepository.findAll())
    }
}