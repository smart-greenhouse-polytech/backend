package ru.polytech.smart.greenhouse.configuration

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@Component
class MqttClientConfig{

    @Bean
    fun mqttClient(
        @Value("\${mqtt.broker}") brokerUrl: String,
        @Value("\${mqtt.client-id}") clientId: String
    ): MqttClient {
        val client = MqttClient(brokerUrl, clientId, MemoryPersistence())
        val options = MqttConnectOptions()
        options.isCleanSession = true
        client.connect(options)
        return client
    }
}