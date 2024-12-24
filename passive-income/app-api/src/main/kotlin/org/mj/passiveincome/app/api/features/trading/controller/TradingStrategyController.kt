package org.mj.passiveincome.app.api.features.trading.controller

import org.mj.passiveincome.app.api.features.trading.service.CreateTradingStrategy
import org.mj.passiveincome.app.api.features.trading.service.TradingStrategyService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TradingStrategyController(
  private val tradingStrategyService: TradingStrategyService,
) {

  /**
   * 거래 전략 생성
   */
  @PostMapping("/trading-strategies")
  fun createTradingStrategy(@RequestBody payload: CreateTradingStrategy): BaseResponse {
    tradingStrategyService.createTradingStrategy(payload)
    return BaseResponse.ok()
  }
}
