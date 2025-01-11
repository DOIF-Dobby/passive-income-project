package org.mj.passiveincome.app.api.config.security.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.passiveincome.app.api.config.security.SimpleAuthenticationEntryPoint
import org.mj.passiveincome.app.api.config.security.oauth2.handler.OAuth2AuthenticationSuccessHandler
import org.mj.passiveincome.app.api.config.security.token.TokenAuthenticationFilter
import org.mj.passiveincome.app.api.config.security.token.TokenAuthenticationService
import org.mj.passiveincome.system.security.oauth2.authentication.OAuth2AuthenticationFilter
import org.mj.passiveincome.system.security.oauth2.authentication.OAuth2AuthenticationProvider
import org.mj.passiveincome.system.security.oauth2.authentication.google.GoogleOAuth2AuthenticationService
import org.mj.passiveincome.system.security.oauth2.redirect.OAuth2RedirectUrlFilter
import org.mj.passiveincome.system.security.oauth2.redirect.OAuth2RedirectUrlResolver
import org.mj.passiveincome.system.security.oauth2.redirect.google.GoogleOAuth2RedirectUrlService
import org.mj.passiveincome.system.security.oauth2.user.OAuth2UserService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.client.RestClient

@Configuration
@ConditionalOnProperty(
  prefix = "app.security",
  name = ["filter-chain"],
  havingValue = "oauth2",
)
class OAuth2SecurityFilterChainConfig(
  private val objectPostProcessor: ObjectPostProcessor<Any>,
  private val simpleAuthenticationEntryPoint: SimpleAuthenticationEntryPoint,
  private val restClient: RestClient,
  private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
  private val oAuth2UserService: OAuth2UserService,
  private val objectMapper: ObjectMapper,
  private val googleOAuth2Properties: GoogleOAuth2Properties,
  private val tokenAuthenticationService: TokenAuthenticationService,
) {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http
      .csrf { it.disable() }
      .httpBasic { it.disable() }
      .formLogin { it.disable() }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .addFilterBefore(oAuth2RedirectUriFilter(), UsernamePasswordAuthenticationFilter::class.java)
      .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
      .addFilterBefore(oAuth2AuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
      .authorizeHttpRequests {
        it.requestMatchers(*permitAllRequest).permitAll()
        it.anyRequest().authenticated()
      }
      .exceptionHandling {
        it.authenticationEntryPoint(simpleAuthenticationEntryPoint)
      }
      .build()
  }

  @Bean
  fun authenticationManager(): AuthenticationManager {

    // 실제 OAuth2 인증을 처리하는 서비스들 목록
    val oAuth2AuthenticationServices = listOf(
      // Google OAuth2 인증 서비스
      GoogleOAuth2AuthenticationService(
        restClient = restClient,
        objectMapper = objectMapper,
        clientId = googleOAuth2Properties.clientId,
        clientSecret = googleOAuth2Properties.clientSecret,
        redirectUri = googleOAuth2Properties.redirectUri
      )
    )

    val provider = OAuth2AuthenticationProvider(
      oAuth2AuthenticationServices = oAuth2AuthenticationServices,
      oAuth2UserService = oAuth2UserService,
    )

    return AuthenticationManagerBuilder(objectPostProcessor)
      .authenticationProvider(provider)
      .build()
  }

  /**
   * OAuth2 인증코드로 인증을 시도하는 필터
   */
  fun oAuth2AuthenticationFilter(): OAuth2AuthenticationFilter {
    val filter = OAuth2AuthenticationFilter(authenticationManager())
    filter.setAuthenticationSuccessHandler(oAuth2AuthenticationSuccessHandler)

    return filter
  }

  /**
   * 발급한 토큰을을 검증하는 필터
   */
  fun tokenAuthenticationFilter(): TokenAuthenticationFilter {
    return TokenAuthenticationFilter(tokenAuthenticationService)
  }

  /**
   * OAuth2 Redirect Uri를 만들어서 반환하는 필터
   */
  fun oAuth2RedirectUriFilter(): OAuth2RedirectUrlFilter {
    val oAuth2RedirectUrlResolver = OAuth2RedirectUrlResolver(
      listOf(
        GoogleOAuth2RedirectUrlService(
          clientId = googleOAuth2Properties.clientId,
          redirectUri = googleOAuth2Properties.redirectUri,
          scope = googleOAuth2Properties.scope
        )
      )
    )

    return OAuth2RedirectUrlFilter(
      oAuth2RedirectUrlResolver = oAuth2RedirectUrlResolver,
      objectMapper = objectMapper
    )
  }


  companion object {
    private val permitAllRequest: Array<RequestMatcher> = arrayOf()
  }
}
