package org.mj.passiveincome.domain.trading

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TradingStrategyRepository : JpaRepository<TradingStrategy, Long> {

  @Query(
    """
    select ts
    from TradingStrategy ts
    join fetch ts.owner
    where ts.id = :id
  """
  )
  fun findTradingStrategy(id: Long): TradingStrategy?
}
