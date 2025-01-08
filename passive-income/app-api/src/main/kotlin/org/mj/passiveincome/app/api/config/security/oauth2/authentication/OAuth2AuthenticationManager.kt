package org.mj.passiveincome.app.api.config.security.oauth2.authentication

import org.mj.passiveincome.app.api.config.security.oauth2.OAuth2AuthenticationException
import org.springframework.stereotype.Component

@Component
class OAuth2AuthenticationManager(
  private val oAuth2AuthenticationServiceList: List<OAuth2AuthenticationService>
) {

  fun authenticate(payload: OAuth2AuthenticationPayload): OAuth2Authentication {
    for (service in oAuth2AuthenticationServiceList) {
      if (service.supports(payload.providerType)) {
        return service.authenticate(
          authorizationCode = payload.authorizationCode,
          redirectUri = payload.redirectUri
        )
      }
    }

    throw OAuth2AuthenticationException()
  }
}
