package org.mj.passiveincome.app.api.config.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

enum class FilterChainType {
  PERMIT_ALL,
  OAUTH2,
}

@Component
@ConfigurationProperties(prefix = "app.security")
data class SecurityProperties(
  var filterChain: FilterChainType = FilterChainType.PERMIT_ALL
)

@Component
@ConfigurationProperties(prefix = "app.security.jwt.access-token")
data class JwtAccessTokenProperties(
  var secret: String = "",
  var expiration: Duration = Duration.ofMinutes(60L)
)
