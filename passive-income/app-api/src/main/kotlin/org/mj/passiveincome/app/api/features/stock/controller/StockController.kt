package org.mj.passiveincome.app.api.features.stock.controller

import jakarta.validation.Valid
import org.mj.passiveincome.app.api.features.stock.service.RegisterStock
import org.mj.passiveincome.app.api.features.stock.service.StockResponse
import org.mj.passiveincome.app.api.features.stock.service.StockService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.web.response.BaseResponseContent
import org.mj.passiveincome.system.web.response.content
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StockController(
  private val stockService: StockService
) {

  @GetMapping("/stocks")
  fun findStocks(): BaseResponseContent<StockResponse> {
    return BaseResponseDetail.content(stockService.findStocks())
  }

  @PostMapping("/stocks")
  fun registerStock(@RequestBody @Valid payload: RegisterStock): BaseResponse {
    stockService.registerStock(payload)
    return BaseResponse.ok()
  }
}
