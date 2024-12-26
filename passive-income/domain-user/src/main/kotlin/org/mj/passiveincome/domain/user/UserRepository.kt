package org.mj.passiveincome.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
  fun findTopByName(name: String): User?
}
