package org.mj.passiveincome.app.api.features.portfolio.interest.controller

import jakarta.validation.Valid
import org.mj.passiveincome.app.api.features.portfolio.interest.service.AddInterestStock
import org.mj.passiveincome.app.api.features.portfolio.interest.service.InterestStockResponse
import org.mj.passiveincome.app.api.features.portfolio.interest.service.InterestStockService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.web.response.BaseResponseContent
import org.mj.passiveincome.system.web.response.content
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class InterestStockController(
  private val interestStockService: InterestStockService,
) {

  /**
   * 관심 주식 추가
   */
  @PostMapping("/interest-stocks")
  fun addInterestStock(@RequestBody @Valid payload: AddInterestStock): BaseResponse {
    interestStockService.addInterestStock(payload)
    return BaseResponse.ok()
  }

  /**
   * 사용자 / 그룹 별 관심 주식 목록 조회
   */
  @GetMapping("/interest-stocks")
  fun findInterestStocks(@RequestParam userId: Long, @RequestParam groupId: Long): BaseResponseContent<InterestStockResponse> {
    val interestStocks = interestStockService.findInterestStocks(
      userId = userId,
      groupId = groupId,
    )
    return BaseResponseDetail.content(interestStocks)
  }
}
