package org.mj.passiveincome.domain.user

import org.mj.passiveincome.domain.common.email.Email
import org.mj.passiveincome.type.common.OAuth2ProviderType

class UserFixtures {

  companion object {
    fun dobby(): User {
      return User(
        name = "Dobby",
        oauth2ProviderType = OAuth2ProviderType.GOOGLE,
        oauth2Subject = "google-oauth-id1",
        email = Email("doif.dobby@gmail.com")
      )
    }

    fun myungJin(): User {
      return User(
        name = "Myungjin",
        oauth2ProviderType = OAuth2ProviderType.GOOGLE,
        oauth2Subject = "google-oauth-id2",
        email = Email("kjpmj27@gmail.com")
      )
    }
  }
}
