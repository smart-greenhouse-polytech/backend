package ru.polytech.smart.greenhouse.mqtt

import jakarta.annotation.PostConstruct
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.polytech.smart.greenhouse.device.DeviceMeasurementEntity
import ru.polytech.smart.greenhouse.device.DeviceMeasurementRepository
import ru.polytech.smart.greenhouse.device.DeviceRepository
import ru.polytech.smart.greenhouse.device.DeviceType

@Service
class SensorService(
    private val mqttClient: IMqttClient,
    private val deviceMeasurementRepository: DeviceMeasurementRepository,
    private val deviceRepository: DeviceRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val readingPattern = "(.+):(.+)".toRegex()

    private val sensorDevices = setOf(
        DeviceType.TEMPERATURE_GROUND, DeviceType.TEMPERATURE_MIDDLE, DeviceType.TEMPERATURE_CEIL,
        DeviceType.HUMIDITY_AIR, DeviceType.HUMIDITY_GROUND, DeviceType.PRESSURE_AIR
    )

    @PostConstruct
    fun init() {
        subscribeToSensors()
    }

    private fun subscribeToSensors() {
        sensorDevices.forEach { type ->
            try {
                mqttClient.subscribe(type.topicName) { _, message ->
                    processSensorData(type, String(message.payload))
                }
                logger.info("Subscribed to ${type.topicName}")
            } catch (e: Exception) {
                logger.error("Error subscribing to ${type.topicName}", e)
            }
        }

    }

    private fun processSensorData(type: DeviceType, payload: String) {
        logger.info("Processing ${type.topicName} with payload $payload")
        val (deviceName, valueStr) = readingPattern.find(payload)?.destructured ?: run {
            logger.warn("Invalid payload format for $type: $payload. Expected 'deviceName:value'")
            return
        }

        val value = valueStr.toDoubleOrNull() ?: run {
            logger.warn("Invalid value format for $type: $valueStr")
            return
        }

        val device = deviceRepository.findByName(deviceName).orElseThrow{ IllegalArgumentException("Not found device with name: $deviceName") }

        val devMes = DeviceMeasurementEntity(
            device = device,
            value = value,
        )

        deviceMeasurementRepository.save(devMes)
        logger.debug("Saved ${type.name} data from $deviceName: $value")
    }
}