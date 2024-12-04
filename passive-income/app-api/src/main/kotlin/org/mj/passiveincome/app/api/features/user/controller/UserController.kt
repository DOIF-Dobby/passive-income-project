package org.mj.passiveincome.app.api.features.user.controller

import org.mj.passiveincome.app.api.features.user.service.CreateUser
import org.mj.passiveincome.app.api.features.user.service.UserService
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.web.response.BaseResponseContent
import org.mj.passiveincome.system.web.response.content
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
  private val userService: UserService,
) {

  @PostMapping("/users")
  fun signUp(@RequestBody payload: CreateUser): BaseResponseContent<String> {
    userService.signUp(payload)
    return BaseResponseDetail.content(listOf("User created", "Please check your email to verify your account"))
  }
}
