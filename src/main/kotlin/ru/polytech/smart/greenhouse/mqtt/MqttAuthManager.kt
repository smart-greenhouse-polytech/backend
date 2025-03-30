package ru.polytech.smart.greenhouse.mqtt

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.polytech.smart.greenhouse.device.Credentials
import ru.polytech.smart.greenhouse.device.DeviceEntity
import ru.polytech.smart.greenhouse.device.DeviceType
import ru.polytech.smart.greenhouse.mqtt.exception.MqttConfigException

// MqttAuthManager.kt
@Service
class MqttAuthManager {
    private val logger = LoggerFactory.getLogger(javaClass)

//    fun configureDeviceAccess(device: DeviceEntity) {
//        val aclRules = when (device.type) {
//            DeviceType.THERMOMETER -> listOf(
//                "topic read sensors/${device.deviceId}/temperature",
//                "topic write devices/${device.deviceId}/status"
//            )
//            DeviceType.WINDOW -> listOf(
//                "topic read actuators/${device.deviceId}/commands",
//                "topic write actuators/${device.deviceId}/status"
//            )
//            else -> listOf(
//                "topic read sensors/${device.deviceId}/+",
//                "topic write devices/${device.deviceId}/status"
//            )
//        }
//
//        updateMosquittoConfig(device.credentials, aclRules)
//    }
//
//    private fun updateMosquittoConfig(credentials: Credentials, aclRules: List<String>) {
//        try {
//            // Реализация обновления конфига Mosquitto
//            // через выполнение команд в контейнере
//            execInContainer("mosquitto_passwd", "-b", "/mosquitto/config/passwd",
//                credentials.username, credentials.password)
//
//            val aclEntry = buildAclEntry(credentials.username, aclRules)
//            appendToAclFile(aclEntry)
//
//            reloadMosquittoConfig()
//        } catch (e: Exception) {
//            logger.error("Failed to update Mosquitto config", e)
//            throw MqttConfigException("Failed to configure device access")
//        }
//    }
//
//    private fun buildAclEntry(username: String, rules: List<String>): String {
//        return buildString {
//            appendLine("user $username")
//            rules.forEach { appendLine(it) }
//        }
//    }
//
//    private fun appendToAclFile(content: String) {
//        try {
//            val cmd = arrayOf(
//                "docker", "exec", containerName,
//                "sh", "-c", "echo '$content' >> /mosquitto/config/acl"
//            )
//            Runtime.getRuntime().exec(cmd).waitFor()
//        } catch (e: Exception) {
//            logger.error("Error appending to ACL file: ${e.message}")
//            throw MqttConfigException("Failed to update ACL file", e)
//        }
//    }
//
//    private fun reloadMosquittoConfig() {
//        try {
//            execInContainer("pkill", "-HUP", "mosquitto")
//            logger.info("Mosquitto config reloaded successfully")
//        } catch (e: Exception) {
//            logger.error("Failed to reload Mosquitto config", e)
//            throw MqttConfigException("Failed to reload Mosquitto configuration")
//        }
//    }
//
//    /**
//     * Выполняет команду внутри Docker-контейнера.
//     * @param command Команда и её аргументы (например: ["mosquitto_passwd", "-b", "/mosquitto/config/passwd", "user", "pass"])
//     * @throws MqttConfigException Если команда завершилась с ошибкой
//     */
//    private fun execInContainer(vararg command: String) {
//        val fullCommand = arrayOf(
//            "docker", "exec",
//            "smart-greenhouse-mosquitto",  // Имя контейнера
//            *command
//        )
//
//        try {
//            val process = Runtime.getRuntime().exec(fullCommand)
//            val exitCode = process.waitFor()
//
//            if (exitCode != 0) {
//                val error = process.errorStream.bufferedReader().readText()
//                throw MqttConfigException("Command failed (exit $exitCode): $error")
//            }
//        } catch (e: Exception) {
//            logger.error("Failed to execute command in container: ${e.message}")
//            throw MqttConfigException("Container command error", e)
//        }
//    }
}