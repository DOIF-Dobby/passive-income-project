package org.mj.passiveincome

import org.mj.passiveincome.app.kis.features.kis.realtime.KisRealtimeWebSocketManager
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@ConfigurationPropertiesScan("org.mj.passiveincome.app.kis.config")
@SpringBootApplication
class KisApplication {

  @Bean
  fun runner(manager: KisRealtimeWebSocketManager): CommandLineRunner {
    return CommandLineRunner {
      manager.test()
    }
  }
}

fun main(args: Array<String>) {
  runApplication<KisApplication>(*args)
}
