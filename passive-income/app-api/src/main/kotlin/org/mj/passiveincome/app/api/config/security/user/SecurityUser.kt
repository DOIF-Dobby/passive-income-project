package org.mj.passiveincome.app.api.config.security.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serial

class SecurityUser(
  private val username: String,
  private val password: String,
  private val authorities: Collection<GrantedAuthority> = emptyList(),
) : UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return authorities.toMutableList()
  }

  override fun getPassword(): String {
    return password
  }

  override fun getUsername(): String {
    return username
  }

  companion object {
    @Serial
    private const val serialVersionUID: Long = 3073000966154533680L
  }
}
