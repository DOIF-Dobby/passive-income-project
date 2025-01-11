package org.mj.passiveincome.system.security.oauth2.authentication.token

import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.security.authentication.AbstractAuthenticationToken
import java.io.Serial

/**
 * OAuth2 인증 시도 시 사용되는 인증 토큰
 */
class OAuth2AuthRequestToken(
  private val provider: OAuth2ProviderType,
  private val authorizationCode: String,
) : AbstractAuthenticationToken(null) {
  init {
    isAuthenticated = false
  }

  override fun getPrincipal(): Any {
    return provider
  }

  override fun getCredentials(): Any {
    return authorizationCode
  }

  companion object {
    @Serial
    private const val serialVersionUID: Long = 4934072850104012057L
  }
}
