package org.mj.passiveincome.domain.portfolio.interest

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InterestStockRepository : JpaRepository<InterestStock, Long> {

  @Query(
    """
    select iStock
    from InterestStock iStock
    join fetch iStock.stock
    where iStock.user.id = :userId
    and iStock.group.id = :groupId
    order by iStock.id desc
  """
  )
  fun findInterestStocks(userId: Long, groupId: Long): List<InterestStock>
}
