package org.mj.passiveincome.domain.portfolio.interest

import org.springframework.data.jpa.repository.JpaRepository

interface RecentlyViewedStockRepository : JpaRepository<RecentlyViewedStock, Long> {
}
