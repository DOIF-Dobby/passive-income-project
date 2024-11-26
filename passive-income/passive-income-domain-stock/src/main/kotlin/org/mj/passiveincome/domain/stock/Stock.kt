package org.mj.passiveincome.domain.stock

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.mj.passiveincome.core.base.BaseEntity
import java.time.LocalDate

@Entity
class Stock(

  val standardCode: String,
  val shortCode: String,
  val nameKor: String,
  val shortNameKor: String,
  val nameEng: String,
  val listingDate: LocalDate,

  @Enumerated(EnumType.STRING)
  val marketType: MarketType,
  @Enumerated(EnumType.STRING)
  val securityType: SecurityType,
  @Enumerated(EnumType.STRING)
  val stockType: StockType,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
) : BaseEntity() {
}
