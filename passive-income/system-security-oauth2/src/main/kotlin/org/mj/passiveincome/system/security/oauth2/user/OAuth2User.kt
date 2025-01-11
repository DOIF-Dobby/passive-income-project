package org.mj.passiveincome.system.security.oauth2.user

import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.security.core.GrantedAuthority

interface OAuth2User {

  fun getUserId(): Long

  fun getSubject(): String

  fun getEmail(): String

  fun getUsername(): String

  fun getProviderType(): OAuth2ProviderType

  fun getAuthorities(): Collection<GrantedAuthority>
}
