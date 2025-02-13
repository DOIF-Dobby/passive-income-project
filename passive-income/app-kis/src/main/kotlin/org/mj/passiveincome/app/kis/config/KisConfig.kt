package org.mj.passiveincome.app.kis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class KisConfig(
  private val kisProperties: KisProperties,
) {

  @Bean
  fun kisRestClient(): RestClient {
    return RestClient.builder()
      .baseUrl(kisProperties.url)
      .defaultHeaders {
        it.set("appkey", kisProperties.appKey)
        it.set("appsecret", kisProperties.appSecret)
      }
      .build()
  }
}

@ConfigurationProperties(prefix = "kis")
class KisProperties(
  val url: String,
  val appKey: String,
  val appSecret: String,
  val wsUrl: String,
)
