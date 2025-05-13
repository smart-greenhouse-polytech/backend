package ru.polytech.smart.greenhouse.mqtt

import org.eclipse.paho.client.mqttv3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class MqttConfig {
    @Value("\${mqtt.broker.url}")
    private val brokerUrl: String? = null

    @Bean
    fun mqttConnectOptions(): MqttConnectOptions {
        val options = MqttConnectOptions()
        options.serverURIs = arrayOf(brokerUrl)
        options.isAutomaticReconnect = true
        options.userName = "admin"
        options.password = "admin".toCharArray()
        options.keepAliveInterval = 60  // 60 секунд — стандартно
        return options
    }

    @Bean
    @Throws(MqttException::class)
    fun mqttClient(): IMqttClient {
        val clientId = "server-client-" + UUID.randomUUID().toString().take(8)
        val client: IMqttClient = MqttClient(brokerUrl, clientId)

        client.connect(mqttConnectOptions())
        client.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                println("MQTT connection lost: ${cause?.message}")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                println("Message arrived on topic $topic: ${message?.toString()}")
                // Не обрабатывай тут — у тебя обработка в subscribe()
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                // для публикации — не важно для подписчика
            }
        })
        if (!client.isConnected) {
            client.connect(mqttConnectOptions())
        }

        return client
    }
}