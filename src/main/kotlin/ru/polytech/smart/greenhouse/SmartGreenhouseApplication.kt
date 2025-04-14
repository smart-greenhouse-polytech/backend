package ru.polytech.smart.greenhouse

import lombok.RequiredArgsConstructor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.polytech.smart.greenhouse.crop.CropsRepository

@SpringBootApplication
@RequiredArgsConstructor
@Component
class SmartGreenhouseApplication

fun main(args: Array<String>) {
    runApplication<SmartGreenhouseApplication>(*args)
}

