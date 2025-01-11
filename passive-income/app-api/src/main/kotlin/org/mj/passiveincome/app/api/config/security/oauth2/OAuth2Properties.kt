package org.mj.passiveincome.app.api.config.security.oauth2

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.security.oauth2.provider.google")
data class GoogleOAuth2Properties(
  var clientId: String = "",
  var clientSecret: String = "",
  var redirectUri: String = "",
  var scope: List<String> = emptyList()
)
