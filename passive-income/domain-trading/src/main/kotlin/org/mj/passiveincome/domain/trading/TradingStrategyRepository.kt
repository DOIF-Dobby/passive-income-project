package org.mj.passiveincome.domain.trading

import org.springframework.data.jpa.repository.JpaRepository

interface TradingStrategyRepository : JpaRepository<TradingStrategy, Long> {
}
