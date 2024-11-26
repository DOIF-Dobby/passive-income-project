package org.mj.passiveincome.app.service.user

import org.mj.passiveincome.core.base.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
  private val userService: UserService,
) {

  @PostMapping("/users")
  fun signUp(@RequestBody request: SignUpRequest): BaseResponse<Unit> {
    userService.signUp(request)
    return BaseResponse.ok()
  }
}
