package ru.polytech.smart.greenhouse.configuration

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mqtt")
class MqttController(private val mqttService: MqttClientService) {

    @PostMapping("/publish")
    fun sendMessage(@RequestParam message: String): String {
        mqttService.publishMessage(message)
        return "Сообщение отправлено: $message"
    }

    @GetMapping("/receive")
    fun receiveMessage(): String {
        return mqttService.getLastMessage() ?: "Нет новых сообщений"
    }
}