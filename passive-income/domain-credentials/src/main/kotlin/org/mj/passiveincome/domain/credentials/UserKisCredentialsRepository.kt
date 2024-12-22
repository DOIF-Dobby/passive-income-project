package org.mj.passiveincome.domain.credentials

import org.mj.passiveincome.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserKisCredentialsRepository : JpaRepository<UserKisCredentials, Long> {

  fun existsByUser(user: User): Boolean
}
