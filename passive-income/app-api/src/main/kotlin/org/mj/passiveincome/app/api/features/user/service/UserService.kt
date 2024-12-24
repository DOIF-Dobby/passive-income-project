package org.mj.passiveincome.app.api.features.user.service

import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
) {

  /**
   * 사용자 조회
   */
  @Transactional(readOnly = true)
  fun findUser(userId: Long): UserResponse {
    val user = UserServiceHelper.findUser(userRepository, userId)
    return UserResponse.of(user)
  }


  /**
   * 사용자 등록
   */
  @Transactional
  fun signUp(payload: CreateUser) {
    val user = User(
      name = payload.username,
    )

    userRepository.save(user)
  }
}
