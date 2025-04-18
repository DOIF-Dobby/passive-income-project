package org.mj.passiveincome.app.api.features.portfolio.investment.event

import org.mj.passiveincome.app.api.features.stock.service.StockPriceWatchManager
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockActivatedEvent
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockDeactivatedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserInvestmentStockEventHandler(
  private val stockPriceWatchManager: StockPriceWatchManager,
) {

  /**
   * 사용자 투자 주식 활성화 이벤트 핸들러
   */
  @TransactionalEventListener(UserInvestmentStockActivatedEvent::class)
  fun handleUserInvestmentStockActivatedEvent(event: UserInvestmentStockActivatedEvent) {
    stockPriceWatchManager.addWatchTarget(
      stockShortCode = event.stockShortCode,
      marketType = event.marketType
    )
  }

  /**
   * 사용자 투자 주식 비활성화 이벤트 핸들러
   */
  @TransactionalEventListener(UserInvestmentStockDeactivatedEvent::class)
  fun handleUserInvestmentStockDeactivatedEvent(event: UserInvestmentStockDeactivatedEvent) {
    stockPriceWatchManager.removeWatchTarget(
      stockShortCode = event.stockShortCode,
      marketType = event.marketType
    )
  }
}
