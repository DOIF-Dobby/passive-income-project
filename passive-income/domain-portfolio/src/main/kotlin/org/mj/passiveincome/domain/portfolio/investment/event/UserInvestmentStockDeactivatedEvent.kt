package org.mj.passiveincome.domain.portfolio.investment.event

data class UserInvestmentStockDeactivatedEvent(
  val id: Long,
  val stockShortCode: String
)
