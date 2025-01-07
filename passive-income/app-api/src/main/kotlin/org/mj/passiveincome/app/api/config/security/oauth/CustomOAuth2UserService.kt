package org.mj.passiveincome.app.api.config.security.oauth

import org.mj.passiveincome.domain.common.email.Email
import org.mj.passiveincome.domain.user.OAuthProviderType
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
  private val userRepository: UserRepository,
) : DefaultOAuth2UserService() {

  override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
    val oauthUser = super.loadUser(userRequest)
    val user = getOrSaveUser(userRequest!!, oauthUser)

    println(userRequest)
    println(userRequest.accessToken)
    println(oauthUser)

    return BasicOAuth2User(user, oauthUser)
  }

  private fun getOrSaveUser(userRequest: OAuth2UserRequest, oAuth2User: OAuth2User): User {
    val oauthProviderId = oAuth2User.attributes["sub"] as String
    val oAuthProviderType = OAuthProviderType.of(userRequest.clientRegistration.registrationId)

    return userRepository.findByOAuth(
      oauthProviderId = oauthProviderId,
      oauthProviderType = oAuthProviderType
    ) ?: userRepository.save(createUser(oAuth2User, oAuthProviderType))
  }

  fun createUser(oAuth2User: OAuth2User, oAuthProviderType: OAuthProviderType): User {
    return when (oAuthProviderType) {
      OAuthProviderType.GOOGLE -> {
        val oauthProviderId = oAuth2User.attributes["sub"] as String
        val name = oAuth2User.attributes["name"] as String
        val email = oAuth2User.attributes["email"] as String

        User(
          oauthProviderId = oauthProviderId,
          oauthProviderType = oAuthProviderType,
          name = name,
          email = Email.of(email),
        )
      }
    }
  }
}
