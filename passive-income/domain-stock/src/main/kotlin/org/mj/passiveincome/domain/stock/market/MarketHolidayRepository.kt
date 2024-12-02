package org.mj.passiveincome.domain.stock.market

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface MarketHolidayRepository : JpaRepository<MarketHoliday, Long> {

  fun existsByMarketTypeAndDate(marketType: MarketType, date: LocalDate): Boolean
}
