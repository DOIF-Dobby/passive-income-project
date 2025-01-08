package org.mj.passiveincome.app.api.config.security.oauth2.authentication

import org.mj.passiveincome.domain.user.OAuth2ProviderType

data class OAuth2AuthenticationPayload(
  val authorizationCode: String,
  val redirectUri: String,
  val providerType: OAuth2ProviderType
)
