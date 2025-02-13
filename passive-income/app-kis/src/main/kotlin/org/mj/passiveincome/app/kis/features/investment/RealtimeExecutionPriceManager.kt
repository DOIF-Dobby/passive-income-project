package org.mj.passiveincome.app.kis.features.investment

import org.mj.passiveincome.system.data.redis.RedisCommand
import org.springframework.stereotype.Component

@Component
class RealtimeExecutionPriceManager(
  private val redisCommand: RedisCommand,
) {

  /**
   * 실시간 체결가를 받아올 주식 목록에 추가한다.
   */
  fun subscribe(stockShortCode: String, userInvestmentStockId: Long) {
    // 실시간으로 체결가를 받아올 주식 목록을 저장한다.
    redisCommand.addSet(SUBSCRIPTION_STOCK, stockShortCode)

    // 주식별 실시간 체결가를 요청한 UserInvestmentStock Id를 저장한다.
    redisCommand.addSet(keyOfUserInvestmentStockIds(stockShortCode), userInvestmentStockId.toString())
  }

  /**
   * 실시간 체결가를 받아올 주식 목록에서 제거한다.
   */
  fun unsubscribe(stockShortCode: String, userInvestmentStockId: Long) {
    val key = keyOfUserInvestmentStockIds(stockShortCode)

    // 주식별 실시간 체결가를 요청한 UserInvestmentStock Id를 제거한다.
    redisCommand.removeSet(key, userInvestmentStockId.toString())

    // 주식별 실시간 체결가를 요청한 UserInvestmentStock Id가 없으면 실시간 체결가를 받아올 주식 목록에서 제거한다.
    if (redisCommand.isEmptySet(key)) {
      redisCommand.removeSet(SUBSCRIPTION_STOCK, stockShortCode)
    }
  }

  /**
   * 실시간 체결가를 받아올 주식 목록을 반환한다.
   */
  fun getDomesticSubscriptions(): Set<String> {
    return redisCommand.getSet(SUBSCRIPTION_STOCK)
  }

  /**
   * 주식별 실시간 체결가를 요청한 UserInvestmentStock Id 목록을 반환한다.
   */
  fun getUserInvestmentStockIds(stockShortCode: String): Set<Long> {
    return redisCommand.getSet(keyOfUserInvestmentStockIds(stockShortCode))
      .map { it.toLong() }
      .toSet()
  }

  companion object {
    // 실시간 체결가를 받아올 주식 Set Key
    private const val SUBSCRIPTION_STOCK = "subscription:realtime_price:domestic"

    // 주식별 실시간 체결가를 요청한 UserInvestmentStock Id를 저정할 Key prefix
    private const val SUBSCRIPTION_ID_BY_STOCK = "subscription:realtime_price"

    private fun keyOfUserInvestmentStockIds(stockShortCode: String): String {
      return "$SUBSCRIPTION_ID_BY_STOCK:${stockShortCode}"
    }
  }
}
