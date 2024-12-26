package org.mj.passiveincome.app.api.config.security.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl : UserDetailsService {
  override fun loadUserByUsername(username: String?): UserDetails {
    // TODO: 클라이언트 코드 작성하면 Oauth 적용하고 어떻게 할지 고민하기

    // 1234
    val password = "\$2a\$10\$H1KGpGVgHEokrGBOi/BbJOIS5PNHs9g6UyTeYyiLQLGnga6nPYDcW"

    return SecurityUser(
      username = "mj",
      password = password,
    )
  }
}
