package org.mj.passiveincome.system.security.oauth2.redirect.google

import org.mj.passiveincome.system.security.oauth2.redirect.OAuth2RedirectUriService
import org.mj.passiveincome.type.common.OAuth2ProviderType

class GoogleOAuth2RedirectUriService(
  private val clientId: String,
  private val redirectUri: String,
  private val scope: List<String>
) : OAuth2RedirectUriService {
  override fun resolveRedirectUri(): String {
    val encodedScopes = encodeURI(scope.joinToString(separator = " "))
    return "${GOOGLE_AUTHORIZATION_URL}?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code&scope=${encodedScopes}"
  }

  override fun supports(providerType: OAuth2ProviderType): Boolean {
    return OAuth2ProviderType.GOOGLE == providerType
  }

  companion object {
    private const val GOOGLE_AUTHORIZATION_URL = "https://accounts.google.com/o/oauth2/auth"
  }
}
