package org.mj.passiveincome.domain.portfolio.interest

import org.springframework.data.jpa.repository.JpaRepository

interface InterestStockRepository : JpaRepository<InterestStock, Long> {
}
