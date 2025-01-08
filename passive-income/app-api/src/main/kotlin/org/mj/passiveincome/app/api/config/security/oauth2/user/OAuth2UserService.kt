package org.mj.passiveincome.app.api.config.security.oauth2.user

import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class OAuth2UserService(
  private val userRepository: UserRepository,
) {

  fun getOrSaveUser(request: OAuth2UserRequest) {
  }
}
