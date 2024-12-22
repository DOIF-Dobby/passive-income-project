package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.UserStrategyStockActivatedEvent
import org.mj.passiveincome.domain.trading.UserStrategyStockDeactivatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserStrategyStockEventHandler {

  @EventListener(UserStrategyStockActivatedEvent::class)
  fun handle(event: UserStrategyStockActivatedEvent) {
    println("UserStrategyStockActivatedEvent: ${event.userStrategyStockId}")
  }

  @EventListener(UserStrategyStockDeactivatedEvent::class)
  fun handle2(event: UserStrategyStockDeactivatedEvent) {
    println("UserStrategyStockDeactivatedEvent: ${event.userStrategyStockId}")
  }
}
