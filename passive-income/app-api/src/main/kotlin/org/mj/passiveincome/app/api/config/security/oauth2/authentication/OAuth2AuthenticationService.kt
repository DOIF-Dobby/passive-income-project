package org.mj.passiveincome.app.api.config.security.oauth2.authentication

import org.mj.passiveincome.domain.user.OAuth2ProviderType

interface OAuth2AuthenticationService {

  fun authenticate(authorizationCode: String, redirectUri: String): OAuth2Authentication

  fun supports(oauth2ProviderType: OAuth2ProviderType): Boolean
}
