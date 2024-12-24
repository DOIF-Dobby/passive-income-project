package org.mj.passiveincome.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * 그룹 사용자 Entity
 */
@Entity
class GroupUser(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "group_user_id")
  val id: Long = 0L,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id")
  val group: Group,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  val user: User,

  @Enumerated(EnumType.STRING)
  val role: GroupUserRole
) {
}
