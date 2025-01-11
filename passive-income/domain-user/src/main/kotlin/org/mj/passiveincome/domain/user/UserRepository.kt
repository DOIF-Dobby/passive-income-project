package org.mj.passiveincome.domain.user

import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
  fun findTopByName(name: String): User?

  @Query(
    """
    SELECT u
    FROM User u
    WHERE u.oauth2Subject = :oauth2Subject
    AND u.oauth2ProviderType = :oauth2ProviderType
    """
  )
  fun findByOAuth(oauth2Subject: String, oauth2ProviderType: OAuth2ProviderType): User?
}
