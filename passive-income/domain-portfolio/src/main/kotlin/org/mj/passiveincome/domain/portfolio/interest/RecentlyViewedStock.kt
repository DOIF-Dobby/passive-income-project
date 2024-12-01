package org.mj.passiveincome.domain.portfolio.interest

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.mj.passiveincome.domain.stock.Stock

@Entity
class RecentlyViewedStock(

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stock_id")
  val stock: Stock,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recently_viewed_stock_id")
  val id: Long = 0L,
) {
}
