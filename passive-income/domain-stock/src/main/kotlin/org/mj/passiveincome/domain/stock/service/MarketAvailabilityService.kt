package org.mj.passiveincome.domain.stock.service

import org.mj.passiveincome.domain.stock.market.MarketHolidayRepository
import org.mj.passiveincome.domain.stock.market.MarketType
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MarketAvailabilityService(
  private val marketHolidayRepository: MarketHolidayRepository,
) {

  /**
   * 마켓 유형에 따라 현재 시간에 시장이 열려 있는지 확인한다.
   */
  fun isMarketOpen(marketType: MarketType): Boolean {
    val now = LocalDateTime.now()

    // TODO: 캐싱 처리가 필요해 보인다.
    if (marketHolidayRepository.existsByMarketTypeAndDate(marketType, now.toLocalDate())) {
      return false
    }

    return marketType.isMarketOpen(now.toLocalTime())
  }
}
