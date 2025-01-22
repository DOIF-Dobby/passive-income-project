package org.mj.passiveincome.domain.portfolio.investment

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.mj.passiveincome.domain.stock.Stock
import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.system.data.jpa.BaseEntity

/**
 * 사용자 투자 Entity
 */
@Entity
class UserInvestment(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_investment_id")
  val id: Long = 0L,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  val user: User,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trading_strategy_id")
  val tradingStrategy: TradingStrategy,

  ) : BaseEntity() {

  @OneToMany(mappedBy = "userInvestment", cascade = [CascadeType.ALL], orphanRemoval = true)
  val userInvestmentStocks = mutableListOf<UserInvestmentStock>()

  /**
   * 주식 추가
   */
  fun addStock(stock: Stock) {
    // 이미 추가된 주식인 경우 중복으로 추가할 수 없다.
    userInvestmentStocks.find { it.stock == stock }?.let {
      throw DuplicateStockInInvestmentException()
    }

    val userInvestmentStock = UserInvestmentStock(
      userInvestment = this,
      stock = stock
    )

    userInvestmentStocks.add(userInvestmentStock)
  }

  /**
   * 주식 제거
   */
  fun removeStock(stock: Stock) {
    userInvestmentStocks.removeIf {
      it.stock == stock
    }
  }

  /**
   * 주식 활성화
   */
  fun activateStock(stock: Stock) {
    userInvestmentStocks.find { it.stock == stock }?.activate()
  }

  /**
   * 주식 비활성화
   */
  fun deactivateStock(stock: Stock) {
    userInvestmentStocks.find { it.stock == stock }?.deactivate()
  }
}
