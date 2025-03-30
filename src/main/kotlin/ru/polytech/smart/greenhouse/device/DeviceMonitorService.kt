package ru.polytech.smart.greenhouse.device

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.polytech.smart.greenhouse.mqtt.MqttClientService
import java.time.LocalDateTime
import java.util.UUID

@Service
class DeviceMonitorService(
    private val deviceRepository: DeviceRepository,
    private val measurementRepository: DeviceMeasurementRepository,
    private val mqttService: MqttClientService
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DeviceMonitorService::class.java)
    }

    @Scheduled(fixedRate = 300000) // Каждые 5 минут
    @Transactional
    fun collectSensorData() {
        deviceRepository.findByTypeIn(listOf(DeviceType.THERMOMETER, DeviceType.HUMIDITY_SENSOR))
            .forEach { device ->
                try {
                    val topic = "sensors/${device.greenhouse.id}/${device.type}"
                    val value = mqttService.getLastMeasurement(topic)?.toDoubleOrNull()

                    value?.let {
                        measurementRepository.save(
                            DeviceMeasurementEntity(
                                id = UUID.randomUUID(),
                                device = device,
                                value = it,
                                createdAt = LocalDateTime.now(),
                            )
                        )
                        updateAverageValues(device)
                    }
                } catch (e: Exception) {
                    logger.error("Error processing device ${device.id}", e)
                    device.status = DeviceStatus.ERROR
                    deviceRepository.save(device)
                }
            }
    }

    private fun updateAverageValues(device: DeviceEntity) {
        val measurements = measurementRepository.findByDeviceIdAndCreatedAtAfter(
            device.id,
            LocalDateTime.now().minusHours(1)
        )

        if (measurements.isNotEmpty()) {
            val average = measurements.map { it.value }.average()
            mqttService.publishMessage("analytics/${device.greenhouse.id}/average/${device.type}", average.toString())
        }
    }
}