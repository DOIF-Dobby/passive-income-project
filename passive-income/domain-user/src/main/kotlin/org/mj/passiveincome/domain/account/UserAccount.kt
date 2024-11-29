package org.mj.passiveincome.domain.account

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.system.data.jpa.BaseEntity

@Entity
class UserAccount(
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val user: User,

  val accountNumber: String,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_account_id")
  val id: Long? = null,
) : BaseEntity() {
}
