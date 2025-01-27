package ru.polytech.smart_greenhouse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SmartGreenhouseApplication

fun main(args: Array<String>) {
	runApplication<SmartGreenhouseApplication>(*args)
}
