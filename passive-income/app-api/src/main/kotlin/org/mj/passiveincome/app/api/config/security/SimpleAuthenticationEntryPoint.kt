package org.mj.passiveincome.app.api.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.system.core.logging.log
import org.mj.passiveincome.system.web.databind.ApiObjectMapper
import org.mj.passiveincome.system.web.response.UnauthorizedResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class SimpleAuthenticationEntryPoint(
  private val objectMapper: ApiObjectMapper,
) : AuthenticationEntryPoint {

  override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
    log.warn("Request: [ ${request!!.method} ${request.requestURI} ] Unauthorized")

    response!!.contentType = MediaType.APPLICATION_JSON_VALUE
    response.characterEncoding = StandardCharsets.UTF_8.name()
    response.status = HttpServletResponse.SC_UNAUTHORIZED

    objectMapper.writeValue(response.writer, UnauthorizedResponse())
  }
}
