package org.mj.passiveincome.app.api.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.system.core.logging.log
import org.mj.passiveincome.system.security.token.TokenService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
  private val tokenService: TokenService,
) : OncePerRequestFilter() {
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val token = extractToken(request)

    if (token.isEmpty()) {
      filterChain.doFilter(request, response)
      return
    }

    val isValid = tokenService.validateToken(token)
    if (!isValid) {
      log.error("Invalid token")
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
      return
    }

    // 토큰 저장
    saveToken(token)

    filterChain.doFilter(request, response)
  }

  /**
   * 토큰 추출
   */
  private fun extractToken(request: HttpServletRequest): String {
    val authorization = request.getHeader(AUTHORIZATION_HEADER) ?: request.getParameter(AUTHORIZATION_HEADER)
    if (authorization.isNullOrEmpty()) {
      return ""
    }

    return if (authorization.startsWith(TOKEN_PREFIX)) {
      authorization.substringAfter(TOKEN_PREFIX)
    } else {
      ""
    }

  }

  private fun saveToken(token: String) {
    val authentication = UsernamePasswordAuthenticationToken(token, null, emptyList())
    SecurityContextHolder.getContext().authentication = authentication
  }

  companion object {
    private const val AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION
    private const val TOKEN_PREFIX = "Bearer "
  }
}
