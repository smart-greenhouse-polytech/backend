//package ru.polytech.smart.greenhouse.device
//
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//import ru.polytech.smart.greenhouse.device.exception.DeviceRegistrationException
//import ru.polytech.smart.greenhouse.mqtt.MqttAuthManager
//import java.util.UUID
//
//@Service
//class DeviceRegistrationService(
//    private val deviceRepository: DeviceRepository,
//    private val mqttAuthManager: MqttAuthManager
//) {
//    private val logger = LoggerFactory.getLogger(javaClass)
////
////    @Transactional
////    fun registerNewDevice(deviceId: String, type: DeviceType, macAddress: String): Credentials {
////        if (deviceRepository.existsByDeviceId(deviceId)) {
////            throw DeviceRegistrationException("Device ID already exists")
////        }
////
////        val credentials = generateCredentials()
//////        val device = DeviceEntity(
//////            deviceId = deviceId,
//////            type = type,
//////            macAddress = macAddress,
//////            credentials = credentials
//////        )
////
////        mqttAuthManager.configureDeviceAccess(device)
////        deviceRepository.save(device)
////
////        logger.info("Registered new device: $deviceId")
////        return credentials
////    }
//
//    private fun generateCredentials(): Credentials {
//        return Credentials(
//            username = "dev_${UUID.randomUUID().toString().substring(0, 8)}",
//            password = UUID.randomUUID().toString()
//        )
//    }
//}