package org.mj.passiveincome.domain.user

import org.mj.passiveincome.system.core.base.BaseException

enum class OAuthProviderType {
  GOOGLE,
  ;

  companion object {
    fun of(value: String): OAuthProviderType {
      return entries.firstOrNull { it.name == value.uppercase() }
        ?: throw UnknownOAuthProviderException()
    }
  }
}

class UnknownOAuthProviderException : BaseException(messageProperty = "oauth-provider.unknown")
