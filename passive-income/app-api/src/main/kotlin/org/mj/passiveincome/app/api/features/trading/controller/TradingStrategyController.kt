package org.mj.passiveincome.app.api.features.trading.controller

import org.mj.passiveincome.app.api.features.trading.facade.RegisterUserStrategyStock
import org.mj.passiveincome.app.api.features.trading.facade.TradingStrategyFacade
import org.mj.passiveincome.app.api.features.trading.service.CreateTradingStrategy
import org.mj.passiveincome.app.api.features.trading.service.TradingStrategyService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TradingStrategyController(
  private val tradingStrategyService: TradingStrategyService,
  private val tradingStrategyFacade: TradingStrategyFacade,
) {

  /**
   * 거래 전략 생성
   */
  @PostMapping("/trading-strategies")
  fun createTradingStrategy(@RequestBody payload: CreateTradingStrategy): BaseResponse {
    tradingStrategyService.createTradingStrategy(payload)
    return BaseResponse.ok()
  }

  /**
   * 거래 전략에 주식 등록
   */
  @PostMapping("/trading-strategies/stocks")
  fun registerStockToTradingStrategy(@RequestBody payload: RegisterUserStrategyStock): BaseResponse {
    tradingStrategyFacade.registerStockToTradingStrategy(payload)
    return BaseResponse.ok()
  }
}
