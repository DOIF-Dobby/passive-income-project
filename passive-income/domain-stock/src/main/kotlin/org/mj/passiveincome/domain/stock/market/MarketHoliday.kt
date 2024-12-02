package org.mj.passiveincome.domain.stock.market

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.mj.passiveincome.system.data.jpa.BaseEntity
import java.time.LocalDate

@Entity
class MarketHoliday(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "market_holiday_id")
  val id: Long = 0L,

  @Enumerated(EnumType.STRING)
  val marketType: MarketType,
  val date: LocalDate,
  val description: String,
) : BaseEntity() {
}
