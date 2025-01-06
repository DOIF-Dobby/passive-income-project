package org.mj.passiveincome.app.api.config.security.oauth

import org.mj.passiveincome.app.api.config.security.SimpleAuthenticationEntryPoint
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
  havingValue = "oauth",
)
class OAuthSecurityFilterChainConfig(
  private val customOAuth2UserService: CustomOAuth2UserService,
  private val simpleAuthenticationEntryPoint: SimpleAuthenticationEntryPoint,
  private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
) {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .csrf { it.disable() }
      .httpBasic { it.disable() }
      .formLogin { it.disable() }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .authorizeHttpRequests {
        it.requestMatchers(*permitAllRequest).permitAll()
        it.anyRequest().authenticated()
      }
      .exceptionHandling {
        it.authenticationEntryPoint(simpleAuthenticationEntryPoint)
      }
      .oauth2Login {
        it.userInfoEndpoint { customizer ->
          customizer.userService(customOAuth2UserService)
        }
        it.successHandler(oAuth2AuthenticationSuccessHandler)
      }
      .build()
  }

  companion object {
    private val permitAllRequest: Array<String> = arrayOf()
  }
}
