package org.mj.passiveincome.domain.trading

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
import org.mj.passiveincome.domain.user.User
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
  var name: String,

  @Column(name = "strategy_description")
  var description: String? = null,

  @Column(name = "strategy_visibility")
  var visibility: TradingStrategyVisibility = TradingStrategyVisibility.PRIVATE,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  val owner: User, // 거래 전략의 소유자

) : BaseEntity() {

  @OneToMany(mappedBy = "tradingStrategy", cascade = [CascadeType.ALL], orphanRemoval = true)
  private val _accessSubjects: MutableList<TradingStrategyAccessSubject> = mutableListOf() // 거래 전략에 접근 가능한 주체 목록

  val accessSubjects: List<TradingStrategyAccessSubject>
    get() = _accessSubjects.toList()

  /**
   * 거래 전략에 접근 가능한 주체를 추가한다.
   */
  fun addAccessibleSubject(subjectId: Long, accessType: TradingStrategyAccessType) {
    val accessSubject = TradingStrategyAccessSubject(
      tradingStrategy = this,
      subjectId = subjectId,
      accessType = accessType
    )

    _accessSubjects.add(accessSubject)
  }

  /**
   * 거래 전략에 접근 가능한 주체를 제거한다.
   */
  fun removeAccessibleSubject(accessId: Long) {
    _accessSubjects.removeIf { it.id == accessId }
  }

}
