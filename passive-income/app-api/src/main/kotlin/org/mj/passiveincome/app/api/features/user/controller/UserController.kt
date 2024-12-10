package org.mj.passiveincome.app.api.features.user.controller

import jakarta.validation.Valid
import org.mj.passiveincome.app.api.features.user.service.CreateUser
import org.mj.passiveincome.app.api.features.user.service.UserService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
  private val userService: UserService,
) {

  /**
   * 사용자 등록
   */
  @PostMapping("/users")
  fun signUp(@RequestBody @Valid payload: CreateUser): BaseResponse {
    userService.signUp(payload)
    return BaseResponse.ok()
  }
}
