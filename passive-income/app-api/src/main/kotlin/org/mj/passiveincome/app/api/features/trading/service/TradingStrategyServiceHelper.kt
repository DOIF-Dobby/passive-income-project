package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.mj.passiveincome.system.web.exception.NotFoundApiException

class TradingStrategyNotFoundException : NotFoundApiException(message = "트레이딩 전략을 찾을 수 없습니다.")

class TradingStrategyServiceHelper {

  companion object {
    fun findTradingStrategy(repository: TradingStrategyRepository, tradingStrategyId: Long): TradingStrategy {
      return repository.findByIdOrThrow(tradingStrategyId) { throw TradingStrategyNotFoundException() }
    }
  }
}
