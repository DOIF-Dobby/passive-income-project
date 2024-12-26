package org.mj.passiveincome.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.mj.passiveincome.system.data.jpa.BaseEntity

/**
 * 그룹 Entity
 */
@Entity
@Table(name = "groups")
class Group(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "group_id")
  val id: Long = 0L,

  val name: String,
) : BaseEntity() {

}
