package org.mj.passiveincome.app.api.config.security

import org.mj.passiveincome.system.security.token.TokenKeyProvider
import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.system.security.token.jwt.HmacKeyProvider
import org.mj.passiveincome.system.security.token.jwt.JwtTokenService
import org.mj.passiveincome.system.web.databind.ApiObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun tokenKeyProvider(): TokenKeyProvider {
    // TODO: 키를 어디에 저장해야할까요?
    return HmacKeyProvider("123456789012345678901234567890123456789012345678901234567890")
  }

  @Bean
  fun tokenService(): TokenService {
    return JwtTokenService(tokenKeyProvider())
  }

  @Bean
  fun securityFilterChain(http: HttpSecurity, objectMapper: ApiObjectMapper): SecurityFilterChain {
    return http
      .csrf { it.disable() }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .addFilterBefore(JwtAuthenticationFilter(tokenService()), UsernamePasswordAuthenticationFilter::class.java)
      .authorizeHttpRequests {
        it.requestMatchers(*permitAllRequest).permitAll()
        it.anyRequest().authenticated()
      }
      .exceptionHandling {
        it.authenticationEntryPoint(JwtAuthenticationEntryPoint(objectMapper))
      }
      .build()
  }


  companion object {
    private val permitAllRequest: Array<String> = arrayOf()
  }
}

