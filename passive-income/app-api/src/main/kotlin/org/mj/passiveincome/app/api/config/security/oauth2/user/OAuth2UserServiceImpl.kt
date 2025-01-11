package org.mj.passiveincome.app.api.config.security.oauth2.user

import org.mj.passiveincome.domain.common.email.Email
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.system.security.oauth2.authentication.OAuth2Authentication
import org.mj.passiveincome.system.security.oauth2.user.DefaultOAuth2User
import org.mj.passiveincome.system.security.oauth2.user.OAuth2User
import org.mj.passiveincome.system.security.oauth2.user.OAuth2UserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class OAuth2UserServiceImpl(
  private val userRepository: UserRepository,
) : OAuth2UserService {
  override fun loadUserByOAuth2Authentication(authentication: OAuth2Authentication): OAuth2User {

    // 기존 유저를 찾아서 반환하거나 없으면 새로운 유저를 생성하고 반환한다.
    val user = userRepository.findByOAuth(
      oauth2Subject = authentication.getSubject(),
      oauth2ProviderType = authentication.getProviderType(),
    ) ?: userRepository.save(
      User(
        email = Email.of(authentication.getEmail()),
        name = authentication.getName(),
        oauth2Subject = authentication.getSubject(),
        oauth2ProviderType = authentication.getProviderType(),
      )
    )

    return DefaultOAuth2User(
      userId = user.id,
      subject = user.oauth2Subject,
      email = user.email.address,
      username = user.name,
      providerType = user.oauth2ProviderType,
      authorities = listOf(
        SimpleGrantedAuthority("ROLE_USER")
      ),
    )
  }
}
