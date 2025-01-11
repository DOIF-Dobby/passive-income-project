package org.mj.passiveincome.system.security.oauth2.redirect

import org.mj.passiveincome.system.security.oauth2.UnsupportedOAuth2ProviderException
import org.mj.passiveincome.type.common.OAuth2ProviderType

class OAuth2RedirectUriResolver(
  private val oAuth2RedirectUriServices: List<OAuth2RedirectUriService>
) {

  fun resolveRedirectUri(providerType: String): String {
    val provider = OAuth2ProviderType.of(providerType) {
      throw UnsupportedOAuth2ProviderException("Unsupported OAuth2 provider type: $providerType")
    }

    return oAuth2RedirectUriServices
      .find { it.supports(provider) }
      ?.resolveRedirectUri()
      ?: throw UnsupportedOAuth2ProviderException("Unsupported OAuth2 provider type: $providerType")
  }
}
