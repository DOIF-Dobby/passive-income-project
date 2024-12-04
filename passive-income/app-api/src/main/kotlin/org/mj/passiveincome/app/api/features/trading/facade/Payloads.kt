package org.mj.passiveincome.app.api.features.trading.facade

data class RegisterUserStrategyStock(
  val userId: Long,
  val stockId: Long,
  val tradingStrategyId: Long,
)
