package org.mj.passiveincome.app.api.config

import jakarta.servlet.Filter
import org.mj.passiveincome.system.web.databind.ApiObjectMapper
import org.mj.passiveincome.system.web.filter.HttpRequestLoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestClient

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

  @Bean
  fun httpRequestLoggingFilter(): FilterRegistrationBean<Filter> {
    val registration = FilterRegistrationBean<Filter>()
    registration.filter = HttpRequestLoggingFilter()
    registration.order = -104
    registration.addUrlPatterns("/*")

    return registration
  }

  @Bean
  fun restClient(): RestClient {
    return RestClient.create()
  }
}
