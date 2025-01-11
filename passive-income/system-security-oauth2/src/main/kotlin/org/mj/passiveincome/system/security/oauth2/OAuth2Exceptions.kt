package org.mj.passiveincome.system.security.oauth2

import org.springframework.security.core.AuthenticationException

class OAuth2AuthenticationException(message: String) : AuthenticationException(message)

class UnsupportedOAuth2ProviderException(providerType: String) : AuthenticationException("Unsupported OAuth2 provider: $providerType")
