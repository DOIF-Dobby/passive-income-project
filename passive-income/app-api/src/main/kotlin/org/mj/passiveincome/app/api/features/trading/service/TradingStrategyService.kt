package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.system.data.findAllWithMap
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradingStrategyService(
  val tradingStrategyRepository: TradingStrategyRepository,
) {

  /**
   * 거래 전략 조회
   */
  @Transactional(readOnly = true)
  fun getTradingStrategy(id: Long): TradingStrategy {
    return tradingStrategyRepository.findByIdOrThrow(id)
  }

  /**
   * 거래 전략 목록 조회
   */
  @Transactional(readOnly = true)
  fun getTradingStrategies(): List<TradingStrategyResponse> {
    return tradingStrategyRepository.findAllWithMap { TradingStrategyResponse.from(it) }
  }

  /**
   * 거래 전략 생성
   */
  @Transactional
  fun createTradingStrategy(request: TradingStrategyCreateRequest) {
    val tradingStrategy = TradingStrategy(
      name = request.strategyName,
    )

    tradingStrategyRepository.save(tradingStrategy)
  }
}
