package org.mj.passiveincome.domain.trading

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
import org.mj.passiveincome.domain.stock.Stock
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.system.data.jpa.BaseEntity

@Entity
class UserStrategyStock(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_strategy_stock_id")
  val id: Long = 0L,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  val user: User,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stock_id")
  val stock: Stock,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trading_strategy_id")
  val tradingStrategy: TradingStrategy,

  @Enumerated(EnumType.STRING)
  val tradingActivateType: TradingActivateType = TradingActivateType.INACTIVE,
) : BaseEntity() {
}
