package org.mj.passiveincome.app.api.config.security.oauth

import org.mj.passiveincome.domain.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class BasicOAuth2User(
  val user: User,
  val oAuth2User: OAuth2User,
) : OAuth2User {
  override fun getName(): String {
    return oAuth2User.name
  }

  override fun getAttributes(): MutableMap<String, Any> {
    return oAuth2User.attributes
  }

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return oAuth2User.authorities
  }

}
