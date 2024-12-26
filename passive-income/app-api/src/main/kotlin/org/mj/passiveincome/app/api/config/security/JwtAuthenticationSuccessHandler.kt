package org.mj.passiveincome.app.api.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.security.token.TokenService
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.nio.charset.StandardCharsets

class JwtAuthenticationSuccessHandler(
  private val objectMapper: ObjectMapper,
  private val tokenService: TokenService,
) : AuthenticationSuccessHandler {
  override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
    // TODO: JWT 토큰 생성해서 Authorization 헤더에 담아서 응답하기

    response!!.contentType = MediaType.APPLICATION_JSON_VALUE
    response.characterEncoding = StandardCharsets.UTF_8.name()
    response.status = HttpServletResponse.SC_OK

    objectMapper.writeValue(response.writer, BaseResponse.ok())
  }
}
