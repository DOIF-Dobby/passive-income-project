package org.mj.passiveincome.system.security.oauth2.user

import org.mj.passiveincome.system.security.oauth2.authentication.token.OAuth2AuthenticatedToken
import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.security.core.context.SecurityContextHolder

class DummyAuthenticationUtil {

  companion object {
    fun setDummyAuthentication(
      userId: Long,
      email: String = "doif.dobby@gmail.com",
      username: String = "Dobby",
    ) {
      val oAuth2User = DefaultOAuth2User(
        userId = userId,
        subject = "1234",
        email = email,
        username = username,
        providerType = OAuth2ProviderType.GOOGLE,
        authorities = listOf()
      )

      SecurityContextHolder.getContext().authentication = OAuth2AuthenticatedToken(oAuth2User)
    }

  }
}
