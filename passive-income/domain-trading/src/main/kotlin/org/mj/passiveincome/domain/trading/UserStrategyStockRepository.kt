package org.mj.passiveincome.domain.trading

import org.springframework.data.jpa.repository.JpaRepository

interface UserStrategyStockRepository : JpaRepository<UserStrategyStock, Long> {
}
