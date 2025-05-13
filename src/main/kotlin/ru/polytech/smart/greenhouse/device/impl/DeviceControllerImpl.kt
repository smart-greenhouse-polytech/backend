package ru.polytech.smart.greenhouse.device.impl

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.device.*
import ru.polytech.smart.greenhouse.mqtt.DeviceControlService
import java.time.LocalDateTime
import java.util.*

@RestController
class DeviceControllerImpl(
    private val deviceRepository: DeviceRepository,
    private val measurementRepository: DeviceMeasurementRepository,
    private val deviceMapper: DeviceMapper,
    private val deviceMeasurementMapper: DeviceMeasurementMapper,
    private val deviceControlService: DeviceControlService
) : DeviceController {

    private val logger = LoggerFactory.getLogger(javaClass)

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
    override fun getCurrentMeasurements(): List<LatestDeviceMeasurementDto> {

        val latestMeasurements = measurementRepository.findLatestMeasurementsOfActiveDevices()

        return latestMeasurements.map {
            LatestDeviceMeasurementDto(
                deviceId = it.device.id!!,
                deviceName = it.device.name,
                deviceType = it.device.type,
                value = it.value,
                measuredAt = it.createdAt
            )
        }.toList()

    }

    override fun controlDevice(id: UUID, command: String): ResponseEntity<String> {
        val device = deviceRepository.findById(id).orElseThrow()

        val deviceId = DeviceIdentifier(device.type, device.name)

        return try {
            deviceControlService.sendCommand(deviceId, command)
            ResponseEntity.ok("Command '${command}' sent to ${device.name} (${device.type})")
        } catch (e: Exception) {
            logger.error("Failed to send command", e)
            ResponseEntity.internalServerError().body("Error: ${e.message}")
        }
    }
}