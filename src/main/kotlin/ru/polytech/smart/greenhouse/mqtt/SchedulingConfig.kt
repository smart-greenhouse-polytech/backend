package ru.polytech.smart.greenhouse.mqtt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class SchedulingConfig {

    @Bean
    fun taskScheduler(): ThreadPoolTaskScheduler {
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.poolSize = 10 // укажите размер пула потоков, который вам подходит
        scheduler.setThreadNamePrefix("scheduler-")
        return scheduler
    }
}
