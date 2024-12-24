package org.mj.passiveincome.domain.user.service

import org.mj.passiveincome.domain.user.GroupUser
import org.mj.passiveincome.domain.user.GroupUserRepository
import org.springframework.stereotype.Service

@Service
class AddGroupUserService(
  private val groupUserRepository: GroupUserRepository,
) {

  /**
   * 그룹 사용자 추가
   */
  fun addGroupUser(entity: GroupUser): GroupUser {
    // 그룹 사용자가 이미 존재하면 예외를 발생시킨다.
    if (groupUserRepository.existsByUserAndGroup(entity.user, entity.group)) {
      throw GroupUserAlreadyExistsException()
    }

    return groupUserRepository.save(entity)
  }
}
