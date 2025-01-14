package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.system.web.exception.NotFoundApiException

class TradingStrategyNotFoundException : NotFoundApiException(messageProperty = "trading_strategy.not-found")

class TradingStrategyServiceHelper {

  companion object {
    /**
     * 거래 전략 조회
     */
    fun findTradingStrategy(repository: TradingStrategyRepository, tradingStrategyId: Long): TradingStrategy {
      return repository.findTradingStrategy(tradingStrategyId) ?: throw TradingStrategyNotFoundException()
    }
  }
}
