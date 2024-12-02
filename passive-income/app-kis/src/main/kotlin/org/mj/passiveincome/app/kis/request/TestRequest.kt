package org.mj.passiveincome.app.kis.request

import org.mj.passiveincome.app.kis.config.KisConfig
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class TestRequest(
  private val kisConfig: KisConfig
) {

  fun testRequest2() {
    val body = RestClient.create(kisConfig.url)
      .get()
      .uri {
        it.path("/uapi/domestic-stock/v1/quotations/inquire-price")
          .queryParam("fid_cond_mrkt_div_code", "J")
          .queryParam("fid_input_iscd", "005930")
          .build()
      }
      .headers {
        it.set("content-type", "application/json")
        it.set(
          "authorization",
          "Bearer ${kisConfig.accessToken}"
        )
        it.set("appkey", kisConfig.appKey)
        it.set("appsecret", kisConfig.appSecret)
        it.set("tr_id", "FHKST01010100")
      }
      .retrieve()
      .body(String::class.java)

    println("Response2: $body")
  }
}
