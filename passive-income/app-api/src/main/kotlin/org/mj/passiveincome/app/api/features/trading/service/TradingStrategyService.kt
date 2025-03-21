package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.system.data.findAllWithMap
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradingStrategyService(
  val tradingStrategyRepository: TradingStrategyRepository,
  val userRepository: UserRepository,
) {

  /**
   * 거래 전략 조회
   */
  @Transactional(readOnly = true)
  fun findTradingStrategy(tradingStrategyId: Long): TradingStrategyResponse {
    val tradingStrategy = TradingStrategyServiceHelper.findTradingStrategy(tradingStrategyRepository, tradingStrategyId)
    return TradingStrategyResponse.of(tradingStrategy)
  }

  /**
   * 거래 전략 목록 조회
   */
  @Transactional(readOnly = true)
  fun findTradingStrategies(): List<TradingStrategyResponse> {
    return tradingStrategyRepository.findAllWithMap { TradingStrategyResponse.of(it) }
  }

  /**
   * 거래 전략 생성
   */
  @Transactional
  fun createTradingStrategy(payload: CreateTradingStrategy) {
    val user = UserServiceHelper.findCurrentUser(userRepository)

    val tradingStrategy = TradingStrategy(
      name = payload.strategyName,
      description = payload.strategyDescription,
      owner = user,
      visibility = payload.strategyVisibility,
    )

    tradingStrategyRepository.save(tradingStrategy)
  }
}
