package org.mj.passiveincome.app.api.features.user.service

import org.mj.passiveincome.domain.user.User

data class CreateUser(
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
