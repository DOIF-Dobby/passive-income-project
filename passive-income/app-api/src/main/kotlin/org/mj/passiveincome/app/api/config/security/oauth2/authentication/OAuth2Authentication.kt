package org.mj.passiveincome.app.api.config.security.oauth2.authentication

import org.mj.passiveincome.domain.user.OAuth2ProviderType

interface OAuth2Authentication {

  fun getSubject(): String

  fun getEmail(): String

  fun getProviderType(): OAuth2ProviderType
}
