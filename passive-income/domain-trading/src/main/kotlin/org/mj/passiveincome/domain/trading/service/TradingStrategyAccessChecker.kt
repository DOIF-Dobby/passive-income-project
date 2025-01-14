package org.mj.passiveincome.domain.trading.service

import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.TradingStrategyAccessType
import org.mj.passiveincome.domain.trading.TradingStrategyVisibility
import org.mj.passiveincome.domain.user.GroupUserRepository
import org.mj.passiveincome.domain.user.User
import org.springframework.stereotype.Service

@Service
class TradingStrategyAccessChecker(
  private val groupUserRepository: GroupUserRepository,
) {

  /**
   * 전략에 대한 접근 가능 여부를 확인하고 접근 불가능하면 예외를 발생시킨다.
   */
  fun checkAccess(targetUser: User, tradingStrategy: TradingStrategy) {
    if (!canAccess(targetUser, tradingStrategy)) {
      throw CannotAccessTradingStrategyException()
    }
  }

  /**
   * 전략에 대한 접근 가능 여부를 확인한다.
   */
  fun canAccess(targetUser: User, tradingStrategy: TradingStrategy): Boolean {
    // 전략이 공개 상태이면 누구나 접근 가능하다.
    if (tradingStrategy.visibility == TradingStrategyVisibility.PUBLIC) {
      return true
    }

    // 거래 전략의 소유자는 접근 가능하다.
    if (tradingStrategy.owner == targetUser) {
      return true
    }

    val accessSubjects = tradingStrategy.accessSubjects

    // 전략에 접근 가능한 주체가 없으면 접근 불가능하다.
    if (accessSubjects.isEmpty()) {
      return false
    }

    // 사용자가 전략에 접근할 수 있는 그룹에 속해있으면 접근 가능하다.
    val groupUsers = groupUserRepository.findAllByUser(targetUser)
    val matchedGroupUser = groupUsers.find { groupUser ->
      accessSubjects.any { subject ->
        subject.subjectId == groupUser.group.id && subject.accessType == TradingStrategyAccessType.GROUP
      }
    }

    if (matchedGroupUser != null) {
      return true
    }

    // 전략에 접근 가능한 주체 중에 사용자가 포함되어 있으면 접근 가능하다.
    return accessSubjects.any { it.subjectId == targetUser.id && it.accessType == TradingStrategyAccessType.USER }
  }
}
