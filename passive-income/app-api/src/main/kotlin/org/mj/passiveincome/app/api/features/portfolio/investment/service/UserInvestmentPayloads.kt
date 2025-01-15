package org.mj.passiveincome.app.api.features.portfolio.investment.service

/**
 * 사용자 투자 추가
 */
data class AddUserInvestment(
  val tradingStrategyId: Long,
)

/**
 * 사용자 투자 주식 추가
 */
data class AddUserInvestmentStock(
  val stockId: Long,
)

