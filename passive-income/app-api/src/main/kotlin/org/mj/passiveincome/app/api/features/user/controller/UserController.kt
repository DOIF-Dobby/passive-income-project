package org.mj.passiveincome.app.api.features.user.controller

import org.mj.passiveincome.app.api.features.user.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
  private val userService: UserService,
) {

}
