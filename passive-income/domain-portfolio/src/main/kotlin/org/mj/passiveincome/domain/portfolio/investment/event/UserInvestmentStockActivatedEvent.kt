package org.mj.passiveincome.domain.portfolio.investment.event

data class UserInvestmentStockActivatedEvent(
  val id: Long,
  val stockShortCode: String,
)
