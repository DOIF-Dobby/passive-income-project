package org.mj.passiveincome.app.service.user.credentials

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KisCredentialsController(
  private val kisCredentialsService: KisCredentialsService
) {

  @PostMapping("/kis-credentials")
  fun createKisCredentials(@RequestBody request: KisCredentialsRegisterRequest) {
    kisCredentialsService.registerKisCredentials(request)
  }
}
