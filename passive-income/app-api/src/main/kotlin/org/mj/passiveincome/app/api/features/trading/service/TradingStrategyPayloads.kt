package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.TradingStrategy

data class CreateTradingStrategy(
  val strategyName: String,
)

data class TradingStrategyResponse(
  val strategyId: Long,
  val strategyName: String,
) {
  companion object {
    fun of(tradingStrategy: TradingStrategy) = tradingStrategy.run {
      TradingStrategyResponse(
        strategyId = id,
        strategyName = name,
      )
    }
  }
}
