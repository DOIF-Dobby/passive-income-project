package org.mj.passiveincome.app.api.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.system.web.response.UnauthorizedResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.nio.charset.StandardCharsets

class SimpleAuthenticationEntryPoint(
  private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {

  override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
    response!!.contentType = MediaType.APPLICATION_JSON_VALUE
    response.characterEncoding = StandardCharsets.UTF_8.name()
    response.status = HttpServletResponse.SC_UNAUTHORIZED

    objectMapper.writeValue(response.writer, UnauthorizedResponse())
  }
}
