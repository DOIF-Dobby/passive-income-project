package org.mj.passiveincome.app.api.config.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@ConditionalOnProperty(
  prefix = "app.security",
  name = ["filter-chain"],
  havingValue = "permit_all",
)
class PermitAllSecurityFilterChainConfig {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .csrf { it.disable() }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .authorizeHttpRequests {
        it.anyRequest().permitAll()
      }
      .build()
  }
}
