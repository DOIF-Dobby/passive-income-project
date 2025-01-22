package org.mj.passiveincome.app.api.features.portfolio.investment.service

import org.mj.passiveincome.app.api.features.stock.service.StockServiceHelper
import org.mj.passiveincome.app.api.features.trading.service.TradingStrategyServiceHelper
import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.portfolio.investment.UserInvestmentRepository
import org.mj.passiveincome.domain.portfolio.investment.service.AddUserInvestmentService
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInvestmentService(
  private val userRepository: UserRepository,
  private val tradingStrategyRepository: TradingStrategyRepository,
  private val addUserInvestmentService: AddUserInvestmentService,
  private val userInvestmentRepository: UserInvestmentRepository,
  private val stockRepository: StockRepository,
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

  /**
   * 사용자 투자 주식 추가
   */
  @Transactional
  fun addUserInvestmentStock(userInvestmentId: Long, payload: AddUserInvestmentStock) {
    val userInvestment = UserInvestmentServiceHelper.findUserInvestment(userInvestmentRepository, userInvestmentId)
    val stock = StockServiceHelper.findStock(stockRepository, payload.stockId)

    userInvestment.addStock(stock)
  }

  /**
   * 사용자 투자 주식 제거
   */
  @Transactional
  fun removeUserInvestmentStock(userInvestmentId: Long, stockId: Long) {
    val userInvestment = UserInvestmentServiceHelper.findUserInvestment(userInvestmentRepository, userInvestmentId)
    val stock = StockServiceHelper.findStock(stockRepository, stockId)

    userInvestment.removeStock(stock)
  }


  /**
   * 사용자 투자 주식 활성화
   */
  @Transactional
  fun activateUserInvestmentStock(userInvestmentId: Long, stockId: Long) {
    val userInvestment = UserInvestmentServiceHelper.findUserInvestment(userInvestmentRepository, userInvestmentId)
    val stock = StockServiceHelper.findStock(stockRepository, stockId)

    userInvestment.activateStock(stock)
  }

  /**
   * 사용자 투자 주식 비활성화
   */
  @Transactional
  fun deactivateUserInvestmentStock(userInvestmentId: Long, stockId: Long) {
    val userInvestment = UserInvestmentServiceHelper.findUserInvestment(userInvestmentRepository, userInvestmentId)
    val stock = StockServiceHelper.findStock(stockRepository, stockId)

    userInvestment.deactivateStock(stock)
  }
}
