package ru.polytech.smart.greenhouse.irrigation

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

//@Service
//class IrrigationMqttService(
//    private val mqttClient: MqttClient,
//    @Value("\${mqtt.topics.irrigation}") private val irrigationTopic: String
//) {
//    private val logger = LoggerFactory.getLogger(javaClass)
//
//    fun sendIrrigationCommand(bedId: UUID, volumeLiters: Double) {
//        val topic = "$irrigationTopic/$bedId/command"
//        val payload = """
//            {
//                "action": "START_IRRIGATION",
//                "volume_liters": $volumeLiters,
//                "timestamp": "${Instant.now()}"
//            }
//        """.trimIndent()
//
//        try {
//            mqttClient.publish(topic, MqttMessage(payload.toByteArray()))
//            logger.info("Sent irrigation command to $topic: $payload")
//        } catch (e: MqttException) {
//            logger.error("Failed to send MQTT message", e)
//            throw IrrigationCommandException("Failed to send irrigation command")
//        }
//    }
//}
//
//class IrrigationCommandException(message: String) : RuntimeException(message)