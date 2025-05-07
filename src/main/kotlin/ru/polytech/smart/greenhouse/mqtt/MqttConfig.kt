package ru.polytech.smart.greenhouse.mqtt

import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


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
        return options
    }

    @Bean
    @Throws(MqttException::class)
    fun mqttClient(): IMqttClient {
        val client: IMqttClient = MqttClient(brokerUrl, "server-client")
        client.connect(mqttConnectOptions())
        return client
    }
}