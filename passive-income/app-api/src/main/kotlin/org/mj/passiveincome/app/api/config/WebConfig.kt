package org.mj.passiveincome.app.api.config

import org.mj.passiveincome.system.web.databind.ApiObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class WebConfig {

  @Bean
  fun apiObjectMapper(): ApiObjectMapper {
    return ApiObjectMapper()
  }

  @Bean
  fun mappingJackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter {
    return MappingJackson2HttpMessageConverter(apiObjectMapper())
  }
}
