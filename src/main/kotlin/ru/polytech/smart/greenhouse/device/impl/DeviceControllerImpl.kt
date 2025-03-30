package ru.polytech.smart.greenhouse.device.impl

import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import ru.polytech.smart.greenhouse.mqtt.MqttClientService
import ru.polytech.smart.greenhouse.device.DeviceController
import ru.polytech.smart.greenhouse.device.DeviceEntity
import ru.polytech.smart.greenhouse.device.DeviceMeasurementEntity
import ru.polytech.smart.greenhouse.device.DeviceMeasurementRepository
import ru.polytech.smart.greenhouse.device.DeviceRepository
import ru.polytech.smart.greenhouse.device.DeviceType
import java.time.LocalDateTime
import java.util.UUID

@RestController
class DeviceControllerImpl(
    private val deviceRepository: DeviceRepository,
    private val measurementRepository: DeviceMeasurementRepository,
    private val mqttService: MqttClientService
) : DeviceController {

    @Transactional(readOnly = true)
    override fun getAllDevices(): List<DeviceEntity> {
        return deviceRepository.findAll()
    }

    @Transactional(readOnly = true)
    override fun getDevice(id: UUID): ResponseEntity<DeviceEntity> {
        return deviceRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Transactional(readOnly = true)
    override fun getMeasurements(id: UUID, hours: Int): List<DeviceMeasurementEntity> {
        val cutoff = LocalDateTime.now().minusHours(hours.toLong())
        return measurementRepository.findByDeviceIdAndCreatedAtAfter(id, cutoff)
    }
}