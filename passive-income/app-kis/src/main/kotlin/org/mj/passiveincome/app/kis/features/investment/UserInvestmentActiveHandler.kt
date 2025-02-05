package org.mj.passiveincome.app.kis.features.investment

import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockActivatedEvent
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockDeactivatedEvent
import org.mj.passiveincome.system.kafka.KafkaCommand
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserInvestmentActiveHandler(
  private val kafkaCommand: KafkaCommand,
) {

  /**
   * 사용자 투자 활성화 이벤트 핸들러
   */
  @KafkaListener(topics = ["user-investment-activated"], groupId = "group_1")
  fun handleActivated(message: String) {
    println("Received activated: $message")

    val deserialize = kafkaCommand.deserialize(message, UserInvestmentStockActivatedEvent::class)
    println("deserialize = ${deserialize}")
  }

  /**
   * 사용자 투자 비활성화 이벤트 핸들러
   */
  @KafkaListener(topics = ["user-investment-deactivated"], groupId = "group_1")
  fun handleDeactivated(message: String) {
    println("Received deactivated: $message")

    val deserialize = kafkaCommand.deserialize(message, UserInvestmentStockDeactivatedEvent::class)
    println("deserialize = ${deserialize}")
  }
}
