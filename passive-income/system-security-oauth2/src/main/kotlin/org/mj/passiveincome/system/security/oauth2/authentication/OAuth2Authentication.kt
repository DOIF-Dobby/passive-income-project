package org.mj.passiveincome.system.security.oauth2.authentication

import org.mj.passiveincome.type.common.OAuth2ProviderType

interface OAuth2Authentication {

  fun getSubject(): String

  fun getEmail(): String

  fun getName(): String

  fun getProviderType(): OAuth2ProviderType
}
