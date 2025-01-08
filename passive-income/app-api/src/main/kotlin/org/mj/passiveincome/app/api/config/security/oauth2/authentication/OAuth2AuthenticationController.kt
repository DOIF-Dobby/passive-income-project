package org.mj.passiveincome.app.api.config.security.oauth2.authentication

import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.system.security.token.jwt.JwtTokenContext
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
class OAuth2AuthenticationController(
  private val oAuth2AuthenticationManager: OAuth2AuthenticationManager,
  private val tokenService: TokenService,
) {

  @PostMapping("/oauth2/authenticate")
  fun authenticate(@RequestBody payload: OAuth2AuthenticationPayload): ResponseEntity<BaseResponse> {
    val oAuth2Authentication = oAuth2AuthenticationManager.authenticate(payload)

    val tokenContext = JwtTokenContext(
      subject = oAuth2Authentication.getSubject(),
      expireDuration = Duration.ofHours(1L),
      payload = mapOf(
        "email" to oAuth2Authentication.getEmail(),
        "provider" to oAuth2Authentication.getProviderType()
      )
    )

    val token = tokenService.generateToken(tokenContext)

    return ResponseEntity<BaseResponse>(
      BaseResponse.ok(),
      HttpHeaders().apply {
        setBearerAuth(token.value())
      },
      HttpStatus.OK
    )
  }

}
