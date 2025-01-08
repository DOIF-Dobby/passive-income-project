package org.mj.passiveincome.app.api.config.security.oauth2.user

import org.mj.passiveincome.domain.user.OAuth2ProviderType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class OAuth2User(
  private val name: String,
  private val email: String,
  private val provider: OAuth2ProviderType
) : UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    TODO("Not yet implemented")
  }

  override fun getPassword(): String {
    TODO("Not yet implemented")
  }

  override fun getUsername(): String {
    TODO("Not yet implemented")
  }
}
