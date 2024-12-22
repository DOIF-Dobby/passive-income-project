package org.mj.passiveincome.domain.portfolio.interest

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

/**
 * 관심 그룹 Entity
 */
@Entity
class InterestGroup(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "interest_group_id")
  val id: Long = 0L,

  @Column(name = "group_name")
  val name: String,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  val user: User,

  ) : BaseEntity() {
}
