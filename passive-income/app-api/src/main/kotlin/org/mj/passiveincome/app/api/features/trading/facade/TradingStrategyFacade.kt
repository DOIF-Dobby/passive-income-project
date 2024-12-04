package org.mj.passiveincome.app.api.features.trading.facade

import org.mj.passiveincome.app.api.features.stock.service.StockServiceHelper
import org.mj.passiveincome.app.api.features.trading.service.TradingStrategyServiceHelper
import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.trading.UserStrategyStock
import org.mj.passiveincome.domain.trading.UserStrategyStockRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class TradingStrategyFacade(
  private val userRepository: UserRepository,
  private val stockRepository: StockRepository,
  private val tradingStrategyRepository: TradingStrategyRepository,
  private val userStrategyStockRepository: UserStrategyStockRepository,
) {

  /**
   * 거래 전략에 주식 등록
   */
  fun registerStockToTradingStrategy(payload: RegisterUserStrategyStock) {
    val user = UserServiceHelper.findUser(userRepository, payload.userId)
    val stock = StockServiceHelper.findStock(stockRepository, payload.stockId)
    val tradingStrategy = TradingStrategyServiceHelper.findTradingStrategy(tradingStrategyRepository, payload.tradingStrategyId)

    val userStrategyStock = UserStrategyStock(
      user = user,
      stock = stock,
      tradingStrategy = tradingStrategy,
    )

    userStrategyStockRepository.save(userStrategyStock)
  }
}
