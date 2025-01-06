package org.mj.passiveincome.app.api.config.security

import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.system.web.databind.ApiObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@ConditionalOnProperty(
  prefix = "app.security",
  name = ["filter-chain"],
  havingValue = "jwt",
)
class JwtSecurityFilterChainConfig(
  private val objectMapper: ApiObjectMapper,
  private val objectPostProcessor: ObjectPostProcessor<Any>,
  private val userDetailsService: UserDetailsService,
  private val tokenService: TokenService,
) {

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun authenticationManager(): AuthenticationManager {
    val provider = DaoAuthenticationProvider()
    provider.setPasswordEncoder(passwordEncoder())
    provider.setUserDetailsService(userDetailsService)

    return AuthenticationManagerBuilder(objectPostProcessor)
      .authenticationProvider(provider)
      .build()
  }

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .csrf { it.disable() }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .addFilter(usernamePasswordAuthenticationFilter())
      .addFilterBefore(JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter::class.java)
      .authorizeHttpRequests {
        it.requestMatchers(*permitAllRequest).permitAll()
        it.anyRequest().authenticated()
      }
      .exceptionHandling {
        it.authenticationEntryPoint(SimpleAuthenticationEntryPoint(objectMapper))
      }
      .build()
  }

  fun usernamePasswordAuthenticationFilter(): UsernamePasswordAuthenticationFilter {
    val filter = UsernamePasswordAuthenticationFilter()
    filter.setAuthenticationManager(authenticationManager())
    filter.setAuthenticationSuccessHandler(JwtAuthenticationSuccessHandler(objectMapper, tokenService))

    return filter
  }

  companion object {
    private val permitAllRequest: Array<String> = arrayOf()
  }
}
