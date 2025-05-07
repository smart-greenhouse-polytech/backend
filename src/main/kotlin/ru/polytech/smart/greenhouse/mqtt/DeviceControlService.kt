package ru.polytech.smart.greenhouse.mqtt

import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.polytech.smart.greenhouse.device.DeviceIdentifier

@Service
class DeviceControlService(
    private val mqttClient: IMqttClient,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun sendCommand(device: DeviceIdentifier, command: String) {
        try {
            val payload = "${device.deviceName}:$command"
            mqttClient.publish(device.deviceType.topicName, MqttMessage(payload.toByteArray()))
            logger.info("Sent command to ${device.deviceType.name} (${device.deviceName}): $command")
        } catch (e: Exception) {
            logger.error("Error sending command to ${device.deviceName}", e)
        }
    }
}