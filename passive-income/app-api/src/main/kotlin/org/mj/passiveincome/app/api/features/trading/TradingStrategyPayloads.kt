package org.mj.passiveincome.app.api.features.trading

import org.mj.passiveincome.domain.trading.TradingStrategy

data class TradingStrategyCreateRequest(
  val strategyName: String,
)

data class TradingStrategyResponse(
  val id: Long,
  val strategyName: String,
) {
  companion object {
    fun from(tradingStrategy: TradingStrategy): TradingStrategyResponse {
      return TradingStrategyResponse(
        id = tradingStrategy.id,
        strategyName = tradingStrategy.name,
      )
    }
  }
}
