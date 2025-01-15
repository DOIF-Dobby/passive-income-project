package org.mj.passiveincome.app.api.features.portfolio.investment.controller

import org.mj.passiveincome.app.api.features.portfolio.investment.service.AddUserInvestment
import org.mj.passiveincome.app.api.features.portfolio.investment.service.AddUserInvestmentStock
import org.mj.passiveincome.app.api.features.portfolio.investment.service.UserInvestmentService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserInvestmentController(
  private val userInvestmentService: UserInvestmentService,
) {

  /**
   * 사용자 투자 추가
   */
  @PostMapping("/user-investments")
  fun addUserInvestment(@RequestBody payload: AddUserInvestment): BaseResponse {
    userInvestmentService.addUserInvestment(payload)
    return BaseResponse.ok()
  }

  /**
   * 사용자 투자 주식 추가
   */
  @PostMapping("/user-investment/{userInvestmentId}/stocks")
  fun addUserInvestmentStock(
    @PathVariable("userInvestmentId") userInvestmentId: Long,
    @RequestBody payload: AddUserInvestmentStock
  ): BaseResponse {
    userInvestmentService.addUserInvestmentStock(
      userInvestmentId = userInvestmentId,
      payload = payload
    )

    return BaseResponse.ok()
  }

  /**
   * 사용자 투자 주식 제거
   */
  @DeleteMapping("/user-investment/{userInvestmentId}/stocks/{stockId}")
  fun removeUserInvestmentStock(
    @PathVariable("userInvestmentId") userInvestmentId: Long,
    @PathVariable("stockId") stockId: Long
  ): BaseResponse {
    userInvestmentService.removeUserInvestmentStock(
      userInvestmentId = userInvestmentId,
      stockId = stockId
    )

    return BaseResponse.ok()
  }
}
