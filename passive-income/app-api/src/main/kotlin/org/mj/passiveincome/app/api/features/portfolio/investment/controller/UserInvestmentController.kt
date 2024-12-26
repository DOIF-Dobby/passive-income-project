package org.mj.passiveincome.app.api.features.portfolio.investment.controller

import org.mj.passiveincome.app.api.features.portfolio.investment.service.AddUserInvestment
import org.mj.passiveincome.app.api.features.portfolio.investment.service.UserInvestmentService
import org.mj.passiveincome.system.core.base.BaseResponse
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
}
