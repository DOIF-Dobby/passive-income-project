package org.mj.passiveincome.app.kis.features.kis.realtime

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.passiveincome.app.kis.config.KisProperties
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class KisRealtimeWebSocketManager(
  private val kisProperties: KisProperties
) {

//  fun test() {
//
//    val headers = WebSocketHttpHeaders()
//    val webSocketClient = StandardWebSocketClient()
//    webSocketClient.execute(TestHandler(), headers, URI.create(kisProperties.wsUrl))
//  }
}

class TestHandler : TextWebSocketHandler() {

  override fun afterConnectionEstablished(session: WebSocketSession) {
    println("Connected ${session.id}")

    val payload = mapOf(
      "header" to mapOf(
        "approval_key" to "",
        "custtype" to "P",
        "tr_type" to "1",
        "content-type" to "utf-8"
      ),
      "body" to mapOf(
        "input" to mapOf(
          "tr_id" to "H0STCNT0",
          "tr_key" to "005930"
        )
      )
    )
    val objectMapper = ObjectMapper()
    val message = objectMapper.writeValueAsString(payload)
    session.sendMessage(TextMessage(message))
  }

  override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
    println("Received ${message.payload}")
  }

  override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
    println("Error ${exception.message}")
  }
}
