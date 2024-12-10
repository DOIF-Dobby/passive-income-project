package org.mj.passiveincome.app.api.features.portfolio.interest.service

import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.portfolio.interest.InterestGroup
import org.mj.passiveincome.domain.portfolio.interest.InterestGroupRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class InterestGroupService(
  private val interestGroupRepository: InterestGroupRepository,
  private val userRepository: UserRepository,
) {

  /**
   * 관심 그룹 생성
   */
  fun createInterestGroup(payload: CreateInterestGroup) {
    val interestGroup = InterestGroup(
      name = payload.name,
      user = UserServiceHelper.findUser(userRepository, payload.userId),
    )

    interestGroupRepository.save(interestGroup)
  }

  /**
   * 사용자 관심 그룹 목록
   */
  fun findInterestGroups(userId: Long): List<InterestGroupResponse> {
    return interestGroupRepository.findAllByUserId(userId)
      .map { InterestGroupResponse.of(it) }
  }

}
