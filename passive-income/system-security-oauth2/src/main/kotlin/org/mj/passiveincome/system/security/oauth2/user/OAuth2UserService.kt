package org.mj.passiveincome.system.security.oauth2.user

import org.mj.passiveincome.system.security.oauth2.authentication.OAuth2Authentication


interface OAuth2UserService {

  fun loadUserByOAuth2Authentication(authentication: OAuth2Authentication): OAuth2User
}
