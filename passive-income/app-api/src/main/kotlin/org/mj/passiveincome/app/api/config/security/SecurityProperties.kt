package org.mj.passiveincome.app.api.config.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.security")
data class SecurityProperties(
  var filterChain: String = ""
)

@Component
@ConfigurationProperties(prefix = "app.security.oauth2.provider.google")
data class GoogleOAuth2Properties(
  var clientId: String = "",
  var clientSecret: String = ""
)
