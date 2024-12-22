package org.mj.passiveincome.domain.trading

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.mj.passiveincome.system.data.jpa.BaseEntity

/**
 * 거래 전략 Entity
 */
@Entity
class TradingStrategy(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trading_strategy_id")
  val id: Long = 0L,

  @Column(name = "strategy_name")
  val name: String,

  ) : BaseEntity() {

}
