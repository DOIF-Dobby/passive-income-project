package org.mj.passiveincome.app.api.features.account.controller

import org.mj.passiveincome.app.api.features.account.service.AccountService
import org.mj.passiveincome.app.api.features.account.service.RegisterAccount
import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
  private val accountService: AccountService
) {

  @PostMapping("/account")
  fun registerAccount(@RequestBody payload: RegisterAccount): BaseResponse {
    accountService.registerAccount(payload)

    return BaseResponse.fail()
  }
}
