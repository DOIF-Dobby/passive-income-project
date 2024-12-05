package org.mj.passiveincome.app.api.features.user.service

import jakarta.validation.constraints.NotBlank
import org.mj.passiveincome.domain.user.User

data class CreateUser(
  @field:NotBlank
  val username: String,
)

data class UserResponse(
  val id: Long,
  val username: String,
) {
  companion object {
    fun of(user: User) = user.run {
      UserResponse(
        id = id,
        username = username,
      )
    }
  }
}
