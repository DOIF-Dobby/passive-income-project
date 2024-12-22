package org.mj.passiveincome.app.api.features.credentials.controller

import org.mj.passiveincome.app.api.features.credentials.service.KisCredentialsService
import org.mj.passiveincome.app.api.features.credentials.service.RegisterKisCredentials
import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KisCredentialsController(
  private val kisCredentialsService: KisCredentialsService
) {

  /**
   * KIS Credentials 등록
   */
  @PostMapping("/kis-credentials")
  fun createKisCredentials(@RequestBody payload: RegisterKisCredentials): BaseResponse {
    kisCredentialsService.registerKisCredentials(payload)
    return BaseResponse.ok()
  }
}
