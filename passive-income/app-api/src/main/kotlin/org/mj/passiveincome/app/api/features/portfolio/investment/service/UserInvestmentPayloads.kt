package org.mj.passiveincome.app.api.features.portfolio.investment.service

/**
 * 사용자 투자 추가
 */
data class AddUserInvestment(
  val userId: Long,
  val tradingStrategyId: Long,
)
