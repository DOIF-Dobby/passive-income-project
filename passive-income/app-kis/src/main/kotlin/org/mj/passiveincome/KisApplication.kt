package org.mj.passiveincome

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan("org.mj.passiveincome.app.kis.config")
@SpringBootApplication
class KisApplication

fun main(args: Array<String>) {
  runApplication<KisApplication>(*args)
}
