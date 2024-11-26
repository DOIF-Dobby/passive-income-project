package org.mj.passiveincome.app.service.user.account

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
  private val accountService: AccountService
) {

  @PostMapping("/account")
  fun registerAccount(@RequestBody request: AccountRegisterRequest) {
    accountService.registerAccount(request)
  }
}
