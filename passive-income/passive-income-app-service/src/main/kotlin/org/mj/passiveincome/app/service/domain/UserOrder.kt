package org.mj.passiveincome.app.service.domain

import jakarta.persistence.*
import org.mj.passiveincome.core.money.Money
import java.time.LocalDateTime

@Entity
class UserOrder(
  @Embedded
  @AttributeOverrides(
    AttributeOverride(name = "amount", column = Column(name = "from_amount")),
    AttributeOverride(name = "currency", column = Column(name = "from_currency"))
  )
  val fromMoney: Money,

  @Embedded
  @AttributeOverrides(
    AttributeOverride(name = "amount", column = Column(name = "to_amount")),
    AttributeOverride(name = "currency", column = Column(name = "to_currency"))
  )
  val toMoney: Money,
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  @Column(nullable = false, updatable = false)
  val orderDate: LocalDateTime = LocalDateTime.now()

}