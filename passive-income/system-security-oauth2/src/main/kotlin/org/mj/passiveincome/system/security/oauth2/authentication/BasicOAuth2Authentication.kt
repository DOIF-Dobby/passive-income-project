package org.mj.passiveincome.system.security.oauth2.authentication

import org.mj.passiveincome.type.common.OAuth2ProviderType

class BasicOAuth2Authentication(
  private val subject: String,
  private val email: String,
  private val name: String,
  private val providerType: OAuth2ProviderType,
) : OAuth2Authentication {

  override fun getSubject(): String {
    return subject
  }

  override fun getEmail(): String {
    return email
  }

  override fun getName(): String {
    return name
  }

  override fun getProviderType(): OAuth2ProviderType {
    return providerType
  }
}
