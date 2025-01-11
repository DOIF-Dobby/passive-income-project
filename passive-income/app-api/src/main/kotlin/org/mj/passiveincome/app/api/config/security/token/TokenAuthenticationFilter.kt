package org.mj.passiveincome.app.api.config.security.token

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class TokenAuthenticationFilter(
  private val tokenAuthenticationService: TokenAuthenticationService,
) : OncePerRequestFilter() {
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val token = extractToken(request)

    if (token.isEmpty()) {
      filterChain.doFilter(request, response)
      return
    }

    val authentication = tokenAuthenticationService.authenticate(token)
    SecurityContextHolder.getContext().authentication = authentication

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

  companion object {
    private const val AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION
    private const val TOKEN_PREFIX = "Bearer "
  }
}
