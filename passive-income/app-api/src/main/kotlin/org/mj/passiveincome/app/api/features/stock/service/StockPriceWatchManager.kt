package org.mj.passiveincome.app.api.features.stock.service

import org.mj.passiveincome.domain.stock.market.MarketType
import org.mj.passiveincome.system.data.redis.RedisCommand
import org.springframework.stereotype.Component

/**
 * 주식 가격 감시 관리자
 */
@Component
class StockPriceWatchManager(
  private val redisCommand: RedisCommand
) {

  /**
   * 가격 감시를 할 주식 목록에 추가한다.
   */
  fun addWatchTarget(stockShortCode: String, marketType: MarketType) {
    val key = keyOfWatchTarget(marketType)
    redisCommand.addSet(key, stockShortCode)
  }

  /**
   * 가격 감시를 할 주식 목록에서 제거한다.
   */
  fun removeWatchTarget(stockShortCode: String, marketType: MarketType) {
    val key = keyOfWatchTarget(marketType)
    redisCommand.removeSet(key, stockShortCode)
  }

  companion object {
    private fun keyOfWatchTarget(marketType: MarketType): String {
      return "stock-price-watch-targets:${marketType}"
    }
  }
}
