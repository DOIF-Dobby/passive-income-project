package org.mj.passiveincome.app.api.features.portfolio.investment.service

import org.mj.passiveincome.app.api.features.trading.service.TradingStrategyServiceHelper
import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.portfolio.investment.UserInvestment
import org.mj.passiveincome.domain.portfolio.investment.UserInvestmentRepository
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInvestmentService(
  private val userInvestmentRepository: UserInvestmentRepository,
  private val userRepository: UserRepository,
  private val tradingStrategyRepository: TradingStrategyRepository,
) {

  /**
   * 사용자 투자 추가
   */
  @Transactional
  fun addInvestment(payload: AddUserInvestment) {
    val userInvestment = UserInvestment(
      user = UserServiceHelper.findUser(userRepository, payload.userId),
      tradingStrategy = TradingStrategyServiceHelper.findTradingStrategy(tradingStrategyRepository, payload.tradingStrategyId),
    )

    userInvestmentRepository.save(userInvestment)
  }
}
