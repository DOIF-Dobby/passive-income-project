package org.mj.passiveincome.app.kis.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kis")
class KisConfig(
  val url: String
)
