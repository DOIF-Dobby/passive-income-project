package org.mj.passiveincome.app.api.config.security

import org.mj.passiveincome.system.security.oauth2.user.OAuth2User
import org.mj.passiveincome.system.web.exception.UnauthorizedApiException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

class AuthenticationUtil {

  companion object {
    fun getAuthentication(): Authentication? {
      return SecurityContextHolder.getContext().authentication
    }

    fun getOAuth2User(): OAuth2User? {
      return getAuthentication()?.principal as OAuth2User?
    }

    fun getAuthUserId(): Long {
      val oAuth2User = getOAuth2User() ?: throw UnauthorizedApiException()
      return oAuth2User.getUserId()
    }
  }
}
