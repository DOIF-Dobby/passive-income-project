package org.mj.passiveincome.app.api.config.security.oauth2.user

import org.mj.passiveincome.domain.user.OAuth2ProviderType

data class OAuth2UserRequest(
  val oAuth2Subject: String,
  val oAuth2ProviderType: OAuth2ProviderType,
)
