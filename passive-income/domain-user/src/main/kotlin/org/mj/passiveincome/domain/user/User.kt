package org.mj.passiveincome.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.mj.passiveincome.system.core.base.BaseEntity

@Entity
@Table(name = "users")
class User(
  val username: String,

  @Enumerated(EnumType.STRING)
  val status: UserStatus = UserStatus.ACTIVE,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
) : BaseEntity() {

}
