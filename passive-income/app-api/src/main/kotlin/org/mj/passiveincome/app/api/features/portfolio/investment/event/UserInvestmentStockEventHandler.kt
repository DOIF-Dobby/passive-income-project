package org.mj.passiveincome.app.api.features.portfolio.investment.event

import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockActivatedEvent
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockDeactivatedEvent
import org.mj.passiveincome.system.kafka.KafkaCommand
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserInvestmentStockEventHandler(
  private val kafkaCommand: KafkaCommand,
) {

  /**
   * 사용자 투자 주식 활성화 이벤트 핸들러
   */
  @TransactionalEventListener(UserInvestmentStockActivatedEvent::class)
  fun handleUserInvestmentStockActivatedEvent(event: UserInvestmentStockActivatedEvent) {
    println("$event")
    kafkaCommand.send("user-investment-activated", event)
  }

  /**
   * 사용자 투자 주식 비활성화 이벤트 핸들러
   */
  @TransactionalEventListener(UserInvestmentStockDeactivatedEvent::class)
  fun handleUserInvestmentStockDeactivatedEvent(event: UserInvestmentStockDeactivatedEvent) {
    println("$event")
    kafkaCommand.send("user-investment-deactivated", event)
  }
}
