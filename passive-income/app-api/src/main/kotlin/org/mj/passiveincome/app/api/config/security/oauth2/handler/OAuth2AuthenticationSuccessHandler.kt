package org.mj.passiveincome.app.api.config.security.oauth2.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.app.api.config.security.JwtAccessTokenProperties
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.security.oauth2.user.OAuth2User
import org.mj.passiveincome.system.security.token.Token
import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.system.security.token.jwt.JwtTokenContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class OAuth2AuthenticationSuccessHandler(
  private val objectMapper: ObjectMapper,
  private val tokenService: TokenService,
  private val jwtAccessTokenProperties: JwtAccessTokenProperties
) : AuthenticationSuccessHandler {
  override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
    response.contentType = MediaType.APPLICATION_JSON_VALUE
    response.characterEncoding = StandardCharsets.UTF_8.name()
    response.status = HttpServletResponse.SC_OK

    val oAuth2User = authentication.principal as OAuth2User
    val token = generateToken(oAuth2User)
    response.setHeader(HttpHeaders.AUTHORIZATION, token.bearer())

    objectMapper.writeValue(response.writer, BaseResponse.ok())
  }

  private fun generateToken(oAuth2User: OAuth2User): Token {
    val tokenContext = JwtTokenContext(
      subject = oAuth2User.getSubject(),
      expireDuration = jwtAccessTokenProperties.expiration,
      payload = mapOf(
        "userId" to oAuth2User.getUserId(),
        "email" to oAuth2User.getEmail(),
        "username" to oAuth2User.getUsername(),
        "providerType" to oAuth2User.getProviderType().name,
        "authorities" to oAuth2User.getAuthorities().map { it.authority }
      )
    )

    return tokenService.generateToken(tokenContext)
  }
}
