package org.mj.passiveincome.domain.stock

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.mj.passiveincome.domain.stock.market.MarketType
import org.mj.passiveincome.system.data.jpa.BaseEntity
import java.time.LocalDate

/**
 * 주식 Entity
 */
@Entity
class Stock(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "stock_id")
  val id: Long = 0L,

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

  ) : BaseEntity() {
}
