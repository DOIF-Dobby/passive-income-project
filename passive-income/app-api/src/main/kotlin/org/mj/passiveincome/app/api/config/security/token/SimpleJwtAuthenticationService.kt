package org.mj.passiveincome.app.api.config.security.token

import org.mj.passiveincome.system.security.oauth2.authentication.token.OAuth2AuthenticatedToken
import org.mj.passiveincome.system.security.oauth2.user.DefaultOAuth2User
import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class SimpleJwtAuthenticationService(
  private val tokenService: TokenService
) : TokenAuthenticationService {

  override fun authenticate(token: String): Authentication {
    val isValid = tokenService.validateToken(token)
    if (!isValid) {
      throw TokenAuthenticationException()
    }

    return createAuthentication(token)
  }

  private fun createAuthentication(token: String): Authentication {
    try {
      val payloadValues = tokenService.getPayloadValues(token)

      val userId = payloadValues["userId"] as Int
      val subject = payloadValues["sub"] as String
      val email = payloadValues["email"] as String
      val username = payloadValues["username"] as String
      val providerType = OAuth2ProviderType.of(payloadValues["providerType"] as String) { throw TokenAuthenticationException() }
      val authorities = payloadValues["authorities"] as List<*>

      val oAuth2User = DefaultOAuth2User(
        userId = userId.toLong(),
        subject = subject,
        email = email,
        username = username,
        providerType = providerType,
        authorities = authorities.map { SimpleGrantedAuthority(it as String) }
      )

      return OAuth2AuthenticatedToken(oAuth2User)
    } catch (e: Exception) {
      throw TokenAuthenticationException()
    }
  }
}
