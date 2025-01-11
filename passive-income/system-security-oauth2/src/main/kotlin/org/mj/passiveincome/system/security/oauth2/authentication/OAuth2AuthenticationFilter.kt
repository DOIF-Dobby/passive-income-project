package org.mj.passiveincome.system.security.oauth2.authentication

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mj.passiveincome.system.security.oauth2.OAuth2AuthenticationException
import org.mj.passiveincome.system.security.oauth2.authentication.token.OAuth2AuthRequestToken
import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * OAuth2 Authorization Code를 받아서 인증을 처리하는 필터
 */
class OAuth2AuthenticationFilter(
  authenticationManager: AuthenticationManager,
) : AbstractAuthenticationProcessingFilter(
  DEFAULT_ANT_PATH_REQUEST_MATCHER,
  authenticationManager
) {

  var authorizationCodeParameter: String = DEFAULT_AUTHORIZATION_CODE_PARAMETER
  var providerParameter: String = DEFAULT_PROVIDER_PARAMETER

  override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
    val provider = obtainProvider(request)
    val authorizationCode = obtainAuthorizationCode(request)

    val authRequest = OAuth2AuthRequestToken(
      provider = provider,
      authorizationCode = authorizationCode
    )

    return authenticationManager.authenticate(authRequest)
  }

  /**
   * OAuth2 Provider를 추출한다.
   */
  private fun obtainProvider(request: HttpServletRequest): OAuth2ProviderType {
    return OAuth2ProviderType.of(request.getParameter(providerParameter)) {
      throw OAuth2AuthenticationException("Unsupported OAuth2 provider")
    }
  }

  /**
   * Authorization Code를 추출한다.
   */
  private fun obtainAuthorizationCode(request: HttpServletRequest): String {
    return request.getParameter(authorizationCodeParameter)
      ?: throw OAuth2AuthenticationException("Authorization code is required")
  }

  companion object {
    val DEFAULT_ANT_PATH_REQUEST_MATCHER = AntPathRequestMatcher("/oauth2/authenticate", "POST")
    const val DEFAULT_AUTHORIZATION_CODE_PARAMETER = "code"
    const val DEFAULT_PROVIDER_PARAMETER = "provider"
  }
}
