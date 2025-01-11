package org.mj.passiveincome.system.security.oauth2.authentication

import org.mj.passiveincome.type.common.OAuth2ProviderType

interface OAuth2AuthenticationService {

  fun authenticate(authorizationCode: String): OAuth2Authentication

  fun supports(providerType: OAuth2ProviderType): Boolean
}
