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

@Component
class CropsStartupListener(private val cropsRepository: CropsRepository) {
    @EventListener(ApplicationStartedEvent::class)
    fun onApplicationStarted() {
        val crops = cropsRepository.findAll()
        println("Default Crops:")
        crops.forEach { println("- ${it.name} (Water: ${it.waterRequirement}, Temp: ${it.tempMin}-${it.tempMax})") }
    }
}
