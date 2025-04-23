package org.mj.passiveincome.system.security.oauth2.redirect

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.springframework.http.MediaType
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

class OAuth2RedirectUriFilter(
  private val oAuth2RedirectUriResolver: OAuth2RedirectUriResolver,
  private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {
  var requestMatcher: RequestMatcher = DEFAULT_ANT_PATH_REQUEST_MATCHER
  var providerParameter: String = DEFAULT_PROVIDER_PARAMETER

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    if (!requestMatcher.matches(request)) {
      filterChain.doFilter(request, response)
      return
    }
    val providerType = obtainProvider(request)
    val redirectUri = oAuth2RedirectUriResolver.resolveRedirectUri(providerType)

    val redirectUriResponse = BaseResponseDetail.ok(
      detail = mapOf(
        "redirectUri" to redirectUri
      )
    )

    response.contentType = MediaType.APPLICATION_JSON_VALUE
    response.characterEncoding = StandardCharsets.UTF_8.name()
    response.status = HttpServletResponse.SC_OK

    objectMapper.writeValue(response.writer, redirectUriResponse)

  }

  /**
   * OAuth2 Provider를 추출한다.
   */
  private fun obtainProvider(request: HttpServletRequest): String {
    return request.getParameter(providerParameter) ?: ""
  }

  companion object {
    val DEFAULT_ANT_PATH_REQUEST_MATCHER = AntPathRequestMatcher("/oauth2/redirect-uri", "GET")
    const val DEFAULT_PROVIDER_PARAMETER = "provider"
  }
}
