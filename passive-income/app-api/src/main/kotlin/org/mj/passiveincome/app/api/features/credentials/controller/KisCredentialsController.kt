package org.mj.passiveincome.app.api.features.credentials.controller

import org.mj.passiveincome.app.api.features.credentials.service.KisCredentialsRegisterRequest
import org.mj.passiveincome.app.api.features.credentials.service.KisCredentialsService
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
