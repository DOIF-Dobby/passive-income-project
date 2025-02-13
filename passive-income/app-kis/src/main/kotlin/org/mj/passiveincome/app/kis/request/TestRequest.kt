package org.mj.passiveincome.app.kis.request

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class TestRequest(
  private val kisRestClient: RestClient
) {

  fun testRequest2() {
    val body = kisRestClient
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
          "Bearer null"
        )
        it.set("tr_id", "FHKST01010100")
      }
      .retrieve()
      .body(String::class.java)

    println("Response2: $body")
  }
}
