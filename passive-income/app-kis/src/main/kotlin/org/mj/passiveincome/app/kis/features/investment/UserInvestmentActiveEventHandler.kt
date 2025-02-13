package org.mj.passiveincome.app.kis.features.investment

import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockActivatedEvent
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockDeactivatedEvent
import org.mj.passiveincome.system.kafka.KafkaCommand
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserInvestmentActiveEventHandler(
  private val kafkaCommand: KafkaCommand,
  private val realtimeExecutionPriceManager: RealtimeExecutionPriceManager,
) {

  /**
   * 사용자 투자 활성화 이벤트 핸들러
   */
  @KafkaListener(topics = ["user-investment-activated"], groupId = "group_1")
  fun handleActivated(message: String) {
    val event = kafkaCommand.deserialize(message, UserInvestmentStockActivatedEvent::class)

    // 다른 사용자 혹은 같은 사용자여도 다른 거래 전략으로 같은 주식을 활성화 할 수 있다.
    // 그래서 웹소켓으로 실시간 체결가를 받아 올 주식 목록을 관리 해야 한다.
    // 어플리케이션 메모리로 관리하면 서버를 재시작 하면 데이터가 날아가니 Redis에 저장하자.
    realtimeExecutionPriceManager.subscribe(
      stockShortCode = event.stockShortCode,
      userInvestmentStockId = event.id
    )

    // TODO: 소켓으로 주식 실시간 체결가를 받으면 해당 주식을 자동 거래하는 UserInvestment를 찾아서 거래 신호를 발생시킨다.
    // 이거야 말로 Redis에 저장해야하나? DB에는 저장되어 있긴한데, 실시간으로 계속 가격이 변경될테니 I/O가 많아질 것 같다.
    // Redis에 저장하자. 저장해야하는 항목은 사용자 투자 주식 ID

    // 정리하면 이벤트에 있는 주식 코드로 웹소켓을 연결하고 실시간 체결가를 받아서 거래 신호를 발생시킨다.
    // KIS API를 이용하여 실제 주식거래를 하려면 KIS API Key 정보와 토큰이 필요하다.
    // 거래 신호가 발생할 때마다 DB에 저장되어 있는 사용자 정보를 조회하여 주식 매매 API를 호출하는 것은 비효율적이다.
    // 그럼 이 메서드에서 사용자 API Key 정보를 조회하여 Redis에 저장한다.

    // event.stockShortCode <- 이걸 통해서 실시간 체결가를 받아오는 웹소켓을 연결하자.
    // event.id <- 이걸 통해서 사용자 정보를 조회하여 API Key 정보를 Redis에 저장하자.
  }

  /**
   * 사용자 투자 비활성화 이벤트 핸들러
   */
  @KafkaListener(topics = ["user-investment-deactivated"], groupId = "group_1")
  fun handleDeactivated(message: String) {
    val event = kafkaCommand.deserialize(message, UserInvestmentStockDeactivatedEvent::class)

    realtimeExecutionPriceManager.unsubscribe(
      stockShortCode = event.stockShortCode,
      userInvestmentStockId = event.id
    )

  }
}
