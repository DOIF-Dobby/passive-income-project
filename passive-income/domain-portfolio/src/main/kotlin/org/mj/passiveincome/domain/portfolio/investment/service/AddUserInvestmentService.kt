package org.mj.passiveincome.domain.portfolio.investment.service

import org.mj.passiveincome.domain.portfolio.investment.UserInvestment
import org.mj.passiveincome.domain.portfolio.investment.UserInvestmentRepository
import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.service.TradingStrategyAccessChecker
import org.mj.passiveincome.domain.user.User
import org.springframework.stereotype.Service

@Service
class AddUserInvestmentService(
  private val userInvestmentRepository: UserInvestmentRepository,
  private val tradingStrategyAccessChecker: TradingStrategyAccessChecker,
) {

  /**
   * 사용자 투자 추가
   */
  fun addUserInvestment(user: User, tradingStrategy: TradingStrategy): UserInvestment {
    // 유저가 전략에 접근 가능한지 검증한다.
    tradingStrategyAccessChecker.checkAccess(
      targetUser = user,
      tradingStrategy = tradingStrategy,
    )

    val userInvestment = UserInvestment(
      user = user,
      tradingStrategy = tradingStrategy,
    )

    return userInvestmentRepository.save(userInvestment)
  }
}
