package org.mj.passiveincome.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.mj.passiveincome.domain.common.email.Email
import org.mj.passiveincome.system.data.jpa.BaseEntity
import org.mj.passiveincome.type.common.OAuth2ProviderType

/**
 * 사용자 Entity
 */
@Entity
@Table(name = "users")
class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  val id: Long = 0L,

  val name: String,
  val email: Email,

  val oauth2Subject: String,

  @Enumerated(EnumType.STRING)
  val oauth2ProviderType: OAuth2ProviderType,

  @Enumerated(EnumType.STRING)
  val status: UserStatus = UserStatus.ACTIVE,

  ) : BaseEntity() {

}
