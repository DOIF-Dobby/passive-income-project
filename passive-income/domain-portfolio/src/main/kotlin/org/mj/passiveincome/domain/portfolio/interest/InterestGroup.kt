package org.mj.passiveincome.domain.portfolio.interest

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.mj.passiveincome.system.data.jpa.BaseEntity

@Entity
class InterestGroup(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "interest_group_id")
  val id: Long = 0L,

  @Column(name = "group_name")
  val name: String,

  ) : BaseEntity() {
}
