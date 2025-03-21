package org.mj.passiveincome.app.api.features.portfolio.interest.service

import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.portfolio.interest.InterestGroup
import org.mj.passiveincome.domain.portfolio.interest.InterestGroupRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InterestGroupService(
  private val interestGroupRepository: InterestGroupRepository,
  private val userRepository: UserRepository,
) {

  /**
   * 관심 그룹 생성
   */
  @Transactional
  fun createInterestGroup(payload: CreateInterestGroup) {
    val interestGroup = InterestGroup(
      name = payload.name,
      user = UserServiceHelper.findCurrentUser(userRepository),
    )

    interestGroupRepository.save(interestGroup)
  }

  /**
   * 사용자 관심 그룹 목록
   */
  @Transactional(readOnly = true)
  fun findInterestGroups(userId: Long): List<InterestGroupResponse> {
    return interestGroupRepository.findAllByUserId(userId)
      .map { InterestGroupResponse.of(it) }
  }

}
