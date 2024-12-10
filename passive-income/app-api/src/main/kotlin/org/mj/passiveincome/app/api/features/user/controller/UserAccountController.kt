package org.mj.passiveincome.app.api.features.user.controller

import org.mj.passiveincome.app.api.features.user.service.RegisterAccount
import org.mj.passiveincome.app.api.features.user.service.UserAccountService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserAccountController(
  private val userAccountService: UserAccountService
) {

  /**
   * 계좌 등록
   */
  @PostMapping("/account")
  fun registerAccount(@RequestBody payload: RegisterAccount): BaseResponse {
    userAccountService.registerAccount(payload)

    return BaseResponse.fail()
  }
}
