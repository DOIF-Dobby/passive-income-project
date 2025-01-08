package org.mj.passiveincome.domain.user

import org.mj.passiveincome.system.core.base.BaseException

enum class OAuth2ProviderType {
  GOOGLE,
  ;

  companion object {
    fun of(value: String): OAuth2ProviderType {
      return entries.firstOrNull { it.name == value.uppercase() }
        ?: throw UnknownOAuth2ProviderException()
    }
  }
}

class UnknownOAuth2ProviderException : BaseException(messageProperty = "oauth-provider.unknown")
