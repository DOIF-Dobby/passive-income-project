package org.mj.passiveincome.app.api.features.user

import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
) {

  @Transactional
  fun signUp(request: SignUpRequest) {
    val user = User(
      username = request.username,
    )

    userRepository.save(user)
  }
}
