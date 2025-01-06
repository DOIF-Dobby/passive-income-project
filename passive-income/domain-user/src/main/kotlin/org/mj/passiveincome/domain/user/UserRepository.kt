package org.mj.passiveincome.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
  fun findTopByName(name: String): User?

  @Query(
    """
    SELECT u
    FROM User u
    WHERE u.oauthProviderId = :oauthProviderId
    AND u.oauthProviderType = :oauthProviderType
    """
  )
  fun findByOAuth(oauthProviderId: String, oauthProviderType: OAuthProviderType): User?
}
