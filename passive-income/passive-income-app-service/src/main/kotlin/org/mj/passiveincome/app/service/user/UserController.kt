package org.mj.passiveincome.app.service.user

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
  private val userService: UserService,
) {

  @PostMapping("/users")
  fun signUp(@RequestBody request: SignUpRequest) {
    userService.signUp(request)
  }
}
