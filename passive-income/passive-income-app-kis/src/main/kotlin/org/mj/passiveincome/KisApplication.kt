package org.mj.passiveincome

import org.mj.passiveincome.app.kis.request.TestRequest
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@ConfigurationPropertiesScan("org.mj.passiveincome.app.kis.config")
@SpringBootApplication
class KisApplication {

  @Bean
  fun run(testRequest: TestRequest): CommandLineRunner {
    return CommandLineRunner {
      testRequest.testRequest2()
    }
  }
}

fun main(args: Array<String>) {
  runApplication<KisApplication>(*args)
}
