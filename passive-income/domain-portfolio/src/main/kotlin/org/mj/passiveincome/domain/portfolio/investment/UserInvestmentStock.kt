package org.mj.passiveincome.domain.portfolio.investment

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
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockActivatedEvent
import org.mj.passiveincome.domain.portfolio.investment.event.UserInvestmentStockDeactivatedEvent
import org.mj.passiveincome.domain.stock.Stock
import org.mj.passiveincome.system.core.event.EventPublisher
import org.mj.passiveincome.system.data.jpa.BaseEntity

/**
 * 사용자 주식 투자 Entity
 */
@Entity
class UserInvestmentStock internal constructor(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_investment_stock_id")
  val id: Long = 0L,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_investment_id")
  val userInvestment: UserInvestment,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stock_id")
  val stock: Stock
) : BaseEntity() {

  @Enumerated(EnumType.STRING)
  var tradingActivateState: TradingActivateState = TradingActivateState.INACTIVE

  /**
   * 자동 거래 활성화
   */
  internal fun activate() {
    tradingActivateState = TradingActivateState.ACTIVE
    EventPublisher.publishEvent(UserInvestmentStockActivatedEvent(id))
  }

  /**
   * 자동 거래 비활성화
   */
  internal fun deactivate() {
    tradingActivateState = TradingActivateState.INACTIVE
    EventPublisher.publishEvent(UserInvestmentStockDeactivatedEvent(id))
  }
}
