package org.mj.passiveincome.app.api.features.portfolio.investment.service

import org.mj.passiveincome.app.api.features.trading.service.TradingStrategyServiceHelper
import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.portfolio.investment.service.AddUserInvestmentService
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInvestmentService(
  private val userRepository: UserRepository,
  private val tradingStrategyRepository: TradingStrategyRepository,
  private val addUserInvestmentService: AddUserInvestmentService,
) {

  /**
   * 사용자 투자 추가
   */
  @Transactional
  fun addUserInvestment(payload: AddUserInvestment) {
    val user = UserServiceHelper.findCurrentUser(userRepository)
    val tradingStrategy = TradingStrategyServiceHelper.findTradingStrategy(tradingStrategyRepository, payload.tradingStrategyId)

    addUserInvestmentService.addUserInvestment(user, tradingStrategy)
  }
}
