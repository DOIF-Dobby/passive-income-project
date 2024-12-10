package org.mj.passiveincome.domain.portfolio.interest

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RecentlyViewedStockRepository : JpaRepository<RecentlyViewedStock, Long> {

  @Query(
    """
    select rvs
    from RecentlyViewedStock rvs
    join fetch rvs.stock
    where rvs.user.id = :userId
    order by rvs.id desc
  """
  )
  fun findRecentlyViewedStocks(userId: Long): List<RecentlyViewedStock>
}
