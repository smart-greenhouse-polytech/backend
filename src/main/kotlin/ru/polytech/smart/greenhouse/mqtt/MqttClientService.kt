package ru.polytech.smart.greenhouse.mqtt

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class MqttClientService(
    private val client: MqttClient,
    @Value("\${mqtt.topic}") private val baseTopic: String
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MqttClientService::class.java)
    }
    private val messageCache = ConcurrentHashMap<String, String>()

    init {
        client.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                logger.error("MQTT connection lost: ${cause?.message}")
            }

            override fun messageArrived(topic: String, message: MqttMessage) {
                val payload = message.toString()
                logger.info("Received MQTT message: $payload on topic: $topic")
                messageCache[topic] = payload
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                logger.info("Message delivered")
            }
        })

        // Подписка на все подтопики
        client.subscribe("$baseTopic/#")
    }

    fun publishMessage(topicSuffix: String, payload: String) {
        val fullTopic = "$baseTopic/$topicSuffix"
        try {
            client.publish(fullTopic, MqttMessage(payload.toByteArray()))
            logger.info("Published to $fullTopic: $payload")
        } catch (e: MqttException) {
            logger.error("MQTT publish error: ${e.message}")
        }
    }

    fun getLastMeasurement(topicSuffix: String): String? {
        return messageCache["$baseTopic/$topicSuffix"]
    }
}