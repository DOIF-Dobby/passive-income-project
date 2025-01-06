package org.mj.passiveincome.domain.user

import org.mj.passiveincome.domain.common.email.Email

class UserFixtures {

  companion object {
    fun user1(): User {
      return User(
        name = "Test User1",
        oauthProviderType = OAuthProviderType.GOOGLE,
        oauthProviderId = "google-oauth-id1",
        email = Email("doif.dobby@gmail.com")
      )
    }

    fun user2(): User {
      return User(
        name = "Test User2",
        oauthProviderType = OAuthProviderType.GOOGLE,
        oauthProviderId = "google-oauth-id2",
        email = Email("kjpmj27@gmail.com")
      )
    }
  }
}
