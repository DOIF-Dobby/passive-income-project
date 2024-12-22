package org.mj.passiveincome.app.api.features.trading.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.trading.TradingStrategyFixtures
import org.mj.passiveincome.domain.trading.TradingStrategyRepository

@AppApiDataJpaTest
class TradingStrategyServiceTest(
  val tradingStrategyRepository: TradingStrategyRepository,
) : DescribeSpec({
  val tradingStrategyService = TradingStrategyService(tradingStrategyRepository)

  describe("findTradingStrategies") {
    tradingStrategyRepository.saveAll(TradingStrategyFixtures.strategyList())

    it("거래 전략 목록을 조회한다.") {
      val results = tradingStrategyService.findTradingStrategies()

      results.size shouldBe 3
    }
  }

  describe("findTradingStrategy") {
    context("거래 전략이 존재하는 경우") {
      it("거래 전략을 반환한다.") {
        val tradingStrategy = tradingStrategyRepository.save(TradingStrategyFixtures.tradingStrategy1())

        val result = tradingStrategyService.findTradingStrategy(tradingStrategy.id)

        result.strategyId shouldBe tradingStrategy.id
        result.strategyName shouldBe tradingStrategy.name
      }
    }

    context("거래 전략이 존재하지 않는 경우") {
      it("TradingStrategyNotFoundException이 발생한다.") {
        shouldThrow<TradingStrategyNotFoundException> { tradingStrategyService.findTradingStrategy(-1L) }
      }
    }
  }

  describe("createTradingStrategy") {
    it("거래 전략을 생성한다.") {
      val payload = CreateTradingStrategy(
        strategyName = "Test Strategy",
      )

      tradingStrategyService.createTradingStrategy(payload)

      val results = tradingStrategyRepository.findAll()
      results.size shouldBe 1

      val result = results[0]
      result.name shouldBe "Test Strategy"
    }
  }
})
