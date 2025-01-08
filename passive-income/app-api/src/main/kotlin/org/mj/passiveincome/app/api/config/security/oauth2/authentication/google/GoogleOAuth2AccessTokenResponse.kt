package org.mj.passiveincome.app.api.config.security.oauth2.authentication.google

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleOAuth2AccessTokenResponse(
  val accessToken: String,
  val expiresIn: Long,
  val scope: String,
  val tokenType: String,
  val idToken: String,
)
