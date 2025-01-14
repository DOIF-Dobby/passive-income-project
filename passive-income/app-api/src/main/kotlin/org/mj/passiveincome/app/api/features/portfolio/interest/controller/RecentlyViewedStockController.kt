package org.mj.passiveincome.app.api.features.portfolio.interest.controller

import jakarta.validation.Valid
import org.mj.passiveincome.app.api.features.portfolio.interest.service.AddRecentlyViewedStock
import org.mj.passiveincome.app.api.features.portfolio.interest.service.RecentlyViewedStockResponse
import org.mj.passiveincome.app.api.features.portfolio.interest.service.RecentlyViewedStockService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.web.response.BaseResponseContent
import org.mj.passiveincome.system.web.response.content
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RecentlyViewedStockController(
  private val recentlyViewedStockService: RecentlyViewedStockService,
) {

  /**
   * 최근 본 주식 추가
   */
  @PostMapping("/recently-viewed-stocks")
  fun addRecentlyViewedStock(@RequestBody @Valid payload: AddRecentlyViewedStock): BaseResponse {
    recentlyViewedStockService.addRecentlyViewedStock(payload)
    return BaseResponse.ok()
  }

  /**
   * 최근 본 주식 목록 조회
   */
  @GetMapping("/recently-viewed-stocks")
  fun findRecentlyViewedStocks(): BaseResponseContent<RecentlyViewedStockResponse> {
    val recentlyViewedStocks = recentlyViewedStockService.findRecentlyViewedStocks()
    return BaseResponseDetail.content(recentlyViewedStocks)
  }
}
