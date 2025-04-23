package org.mj.passiveincome.app.api.features.user.controller

import org.mj.passiveincome.app.api.features.user.service.RegisterAccount
import org.mj.passiveincome.app.api.features.user.service.UserAccountService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
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

    return BaseResponse.ok()
  }

  @GetMapping("/test")
  fun test(@RequestParam("hello") hello: String): BaseResponseDetail<String> {
    return BaseResponseDetail.ok(hello)
  }

  @PostMapping("/test2")
  fun test2(@RequestBody payload: Test): BaseResponseDetail<Test> {
    return BaseResponseDetail.ok(payload)
  }
}

data class Test(
  val name: String,
  val age: Int
)
