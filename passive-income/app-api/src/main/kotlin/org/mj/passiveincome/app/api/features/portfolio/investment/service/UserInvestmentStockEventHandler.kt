package org.mj.passiveincome.app.api.features.portfolio.investment.service

import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockActivatedEvent
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockDeactivatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserInvestmentStockEventHandler(

) {

  /**
   * 사용자 투자 주식 활성화 이벤트 핸들러
   */
  @EventListener(UserInvestmentStockActivatedEvent::class)
  fun handleUserInvestmentStockActivatedEvent(event: UserInvestmentStockActivatedEvent) {
    println("$event")
  }

  /**
   * 사용자 투자 주식 비활성화 이벤트 핸들러
   */
  @EventListener(UserInvestmentStockDeactivatedEvent::class)
  fun handleUserInvestmentStockDeactivatedEvent(event: UserInvestmentStockDeactivatedEvent) {
    println("$event")
  }
}
