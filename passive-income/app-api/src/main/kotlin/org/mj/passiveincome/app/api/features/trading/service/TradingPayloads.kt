package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.TradingStrategy

/**
 * 거래 전략 생성
 */
data class CreateTradingStrategy(
  val strategyName: String,
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

/**
 * 사용자 거래 전략 및 주식 맵핑 등록
 */
data class RegisterUserStrategyStock(
  val userId: Long,
  val stockId: Long,
  val tradingStrategyId: Long,
)
