package org.mj.passiveincome.domain.portfolio.investment.event

import org.mj.passiveincome.domain.stock.market.MarketType

data class UserInvestmentStockDeactivatedEvent(
  val id: Long,
  val stockShortCode: String,
  val marketType: MarketType,
)
