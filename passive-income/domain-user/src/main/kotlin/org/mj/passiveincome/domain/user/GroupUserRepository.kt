package org.mj.passiveincome.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface GroupUserRepository : JpaRepository<GroupUser, Long> {
  fun existsByUserAndGroup(user: User, group: Group): Boolean
  fun findAllByUser(user: User): MutableList<GroupUser>

}
