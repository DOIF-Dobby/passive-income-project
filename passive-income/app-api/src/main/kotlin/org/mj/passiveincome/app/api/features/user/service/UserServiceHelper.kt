package org.mj.passiveincome.app.api.features.user.service

import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.mj.passiveincome.system.web.exception.NotFoundApiException

class UserNotFoundException : NotFoundApiException(messageProperty = "user.not-found")

class UserServiceHelper {

  companion object {
    fun findUser(repository: UserRepository, userId: Long): User {
      return repository.findByIdOrThrow(userId) { throw UserNotFoundException() }
    }
  }
}
