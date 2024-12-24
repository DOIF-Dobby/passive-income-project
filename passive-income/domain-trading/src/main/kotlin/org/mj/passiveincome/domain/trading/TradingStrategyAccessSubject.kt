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

@Entity
class TradingStrategyAccessSubject internal constructor(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trading_strategy_access_id")
  val id: Long = 0L,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trading_strategy_id")
  val tradingStrategy: TradingStrategy,

  val subjectId: Long, // 접근 대상 ID (사용자 ID 또는 그룹 ID)

  @Enumerated(EnumType.STRING)
  val accessType: TradingStrategyAccessType // 접근 유형
) {
}
