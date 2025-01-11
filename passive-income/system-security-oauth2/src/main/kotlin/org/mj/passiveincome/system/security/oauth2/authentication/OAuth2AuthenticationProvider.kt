package org.mj.passiveincome.system.security.oauth2.authentication

import org.mj.passiveincome.system.security.oauth2.OAuth2AuthenticationException
import org.mj.passiveincome.system.security.oauth2.authentication.token.OAuth2AuthRequestToken
import org.mj.passiveincome.system.security.oauth2.authentication.token.OAuth2AuthenticatedToken
import org.mj.passiveincome.system.security.oauth2.user.OAuth2User
import org.mj.passiveincome.system.security.oauth2.user.OAuth2UserService
import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

class OAuth2AuthenticationProvider(
  private val oAuth2AuthenticationServices: List<OAuth2AuthenticationService>,
  private val oAuth2UserService: OAuth2UserService,
) : AuthenticationProvider {
  init {
    require(oAuth2AuthenticationServices.isNotEmpty()) { "OAuth2AuthenticationServices must not be empty" }
  }

  override fun authenticate(authentication: Authentication): Authentication {
    val providerType = authentication.principal as OAuth2ProviderType
    val authorizationCode = authentication.credentials as String

    // providerType 별 OAuth2AuthenticationService 를 찾아서 인증을 시도한다.
    val oAuth2Authentication = authenticateByProvider(providerType, authorizationCode)
      ?: throw OAuth2AuthenticationException("OAuth2 authentication failed")

    // OAuth2UserService 를 통해 사용자 정보를 가져온다.
    val oAuth2User = oAuth2UserService.loadUserByOAuth2Authentication(oAuth2Authentication)

    return createSuccessAuthentication(oAuth2User)
  }

  override fun supports(authentication: Class<*>): Boolean {
    return OAuth2AuthRequestToken::class.java.isAssignableFrom(authentication)
  }

  /**
   * OAuth2AuthenticationService 를 찾아서 인증을 시도한다.
   */
  private fun authenticateByProvider(providerType: OAuth2ProviderType, authorizationCode: String): OAuth2Authentication? {
    for (service in oAuth2AuthenticationServices) {
      if (service.supports(providerType)) {
        return service.authenticate(authorizationCode = authorizationCode)
      }
    }

    return null
  }

  private fun createSuccessAuthentication(oAuth2User: OAuth2User): Authentication {
    return OAuth2AuthenticatedToken(oAuth2User)
  }
}
