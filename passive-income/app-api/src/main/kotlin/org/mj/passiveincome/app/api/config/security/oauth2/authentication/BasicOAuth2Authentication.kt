package org.mj.passiveincome.app.api.config.security.oauth2.authentication

import org.mj.passiveincome.domain.user.OAuth2ProviderType

class BasicOAuth2Authentication(
  private val subject: String,
  private val email: String,
  private val providerType: OAuth2ProviderType,
) : OAuth2Authentication {

  override fun getSubject(): String {
    return subject
  }

  override fun getEmail(): String {
    return email
  }

  override fun getProviderType(): OAuth2ProviderType {
    return providerType
  }
}
