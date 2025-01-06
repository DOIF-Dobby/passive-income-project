package org.mj.passiveincome.app.api.config.security.oauth

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.system.security.token.jwt.JwtTokenContext
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration

@Component
class OAuth2AuthenticationSuccessHandler(
  private val tokenService: TokenService,
) : AuthenticationSuccessHandler {
  override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
    println(authentication)
    val basicOAuth2User = authentication?.principal as BasicOAuth2User

    val tokenContext = JwtTokenContext(
      subject = basicOAuth2User.user.id.toString(),
      expireDuration = Duration.ofHours(1L),
      payload = mapOf(
        "name" to basicOAuth2User.user.name,
        "email" to basicOAuth2User.user.email.address,
        "id" to basicOAuth2User.user.id,
      )
    )

    val token = tokenService.generateToken(tokenContext)
    response!!.addCookie(Cookie("token", token.get()))

//    response.sendRedirect("http://localhost:3000/oauth/callback")

    response.contentType = MediaType.APPLICATION_JSON_VALUE
    response.characterEncoding = StandardCharsets.UTF_8.name()
    response.status = HttpServletResponse.SC_OK

    response.writer.write("{\"token\": \"${token.get()}\"}")

//    objectMapper.writeValue(response.writer, BaseResponse.ok())
  }
}
