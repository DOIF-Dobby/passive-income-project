package org.mj.passiveincome.system.security.oauth2.user

import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.security.core.GrantedAuthority

class DefaultOAuth2User(
  private val userId: Long,
  private val subject: String,
  private val email: String,
  private val username: String,
  private val providerType: OAuth2ProviderType,
  private val authorities: Collection<GrantedAuthority>
) : OAuth2User {
  override fun getUserId(): Long {
    return userId
  }

  override fun getSubject(): String {
    return subject
  }

  override fun getEmail(): String {
    return email
  }

  override fun getUsername(): String {
    return username
  }

  override fun getProviderType(): OAuth2ProviderType {
    return providerType
  }

  override fun getAuthorities(): Collection<GrantedAuthority> {
    return authorities
  }
}
