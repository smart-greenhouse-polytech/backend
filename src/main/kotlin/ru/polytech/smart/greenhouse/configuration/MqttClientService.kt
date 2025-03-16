package ru.polytech.smart.greenhouse.configuration

import jakarta.annotation.PreDestroy
import org.eclipse.paho.client.mqttv3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class MqttClientService(
    private val client: MqttClient,
    @Value("\${mqtt.topic}") private val topic: String
) {
    private val messageQueue = ConcurrentLinkedQueue<String>()

    init {
        client.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                println("[MQTT] Соединение потеряно: ${cause?.message}")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                val msg = message?.toString() ?: ""
                println("[MQTT] Получено сообщение: $msg на топик: $topic")
                messageQueue.add(msg) // Добавляем сообщение в очередь
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                println("[MQTT] Сообщение отправлено")
            }
        })
        client.subscribe(topic)
        println("[MQTT] Подписка на топик: $topic")
    }

    fun publishMessage(payload: String) {
        try {
            val message = MqttMessage(payload.toByteArray()).apply { qos = 1 }
            client.publish(topic, message)
            println("[MQTT] Отправлено сообщение: $payload")
        } catch (e: MqttException) {
            println("[MQTT] Ошибка отправки сообщения: $e")
        }
    }

    fun getLastMessage(): String? {
        return messageQueue.poll() // Берём последнее сообщение и удаляем его из очереди
    }

    @PreDestroy
    fun cleanup() {
        try {
            if (client.isConnected) {
                client.disconnect()
                println("[MQTT] Клиент отключён")
            }
        } catch (e: MqttException) {
            println("[MQTT] Ошибка отключения: $e")
        }
    }
}
