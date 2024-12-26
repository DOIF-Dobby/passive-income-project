package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.TradingStrategyVisibility

/**
 * 거래 전략 생성
 */
data class CreateTradingStrategy(
  val strategyName: String,
  val strategyDescription: String? = null,
  val strategyVisibility: TradingStrategyVisibility,
)

/**
 * 거래 전략 응답
 */
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

