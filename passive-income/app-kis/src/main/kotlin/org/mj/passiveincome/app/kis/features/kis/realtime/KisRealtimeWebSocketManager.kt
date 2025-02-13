package org.mj.passiveincome.app.kis.features.kis.realtime

import com.fasterxml.jackson.databind.ObjectMapper
import org.mj.passiveincome.app.kis.config.KisProperties
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.net.URI

@Component
class KisRealtimeWebSocketManager(
  private val kisProperties: KisProperties
) {

  fun test() {

    val headers = WebSocketHttpHeaders()
//    headers.add(HttpHeaders.CONTENT_TYPE, "utf-8")
//    headers.add("approval_key", "fc11db8e-113c-4e60-b485-6a7107f586c1")
//    headers.add("custtype", "P")
//    headers.add("tr_type", "1")

    val webSocketClient = StandardWebSocketClient()
    webSocketClient.execute(TestHandler(), headers, URI.create(kisProperties.wsUrl))
  }
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
