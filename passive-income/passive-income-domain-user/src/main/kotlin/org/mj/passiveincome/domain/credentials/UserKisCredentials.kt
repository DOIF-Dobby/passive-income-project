package org.mj.passiveincome.domain.credentials

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.mj.passiveincome.core.base.BaseEntity
import org.mj.passiveincome.domain.user.User

@Entity
class UserKisCredentials(
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val user: User,

  val appKey: String,
  val appSecretKey: String,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
) : BaseEntity() {
}