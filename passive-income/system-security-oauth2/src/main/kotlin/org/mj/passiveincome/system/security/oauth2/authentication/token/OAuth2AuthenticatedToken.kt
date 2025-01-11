package org.mj.passiveincome.system.security.oauth2.authentication.token

import org.mj.passiveincome.system.security.oauth2.user.OAuth2User
import org.springframework.security.authentication.AbstractAuthenticationToken
import java.io.Serial

/**
 * OAuth2 인증 완료 후 생성되는 토큰
 */
class OAuth2AuthenticatedToken(
  private val oAuth2User: OAuth2User,
) : AbstractAuthenticationToken(oAuth2User.getAuthorities()) {
  init {
    isAuthenticated = true
  }

  override fun getPrincipal(): Any {
    return oAuth2User
  }

  override fun getCredentials(): Any {
    return "N/A"
  }

  companion object {
    @Serial
    private const val serialVersionUID: Long = -6790328479522892030L
  }
}
