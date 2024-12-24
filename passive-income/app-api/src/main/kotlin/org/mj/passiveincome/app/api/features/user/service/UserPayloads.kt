package org.mj.passiveincome.app.api.features.user.service

import jakarta.validation.constraints.NotBlank
import org.mj.passiveincome.domain.user.User

/**
 * 사용자 생성
 */
data class CreateUser(
  @field:NotBlank
  val username: String,
)

/**
 * 사용자 응답
 */
data class UserResponse(
  val id: Long,
  val username: String,
) {
  companion object {
    fun of(user: User) = user.run {
      UserResponse(
        id = id,
        username = name,
      )
    }
  }
}
