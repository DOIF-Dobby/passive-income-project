package org.mj.passiveincome.app.api.features.trading.service

import AppApiDataJpaTest
import DummyAuthUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.trading.TradingStrategyFixtures
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.trading.TradingStrategyVisibility
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class TradingStrategyServiceTest(
  val tradingStrategyRepository: TradingStrategyRepository,
  val userRepository: UserRepository,
) : DescribeSpec({
  val tradingStrategyService = TradingStrategyService(
    tradingStrategyRepository = tradingStrategyRepository,
    userRepository = userRepository,
  )

  describe("findTradingStrategies") {
    val user1 = userRepository.save(UserFixtures.dobby())
    val user2 = userRepository.save(UserFixtures.myungJin())

    val tradingStrategy1 = TradingStrategyFixtures.tradingStrategy(
      owner = user1
    )
    val tradingStrategy2 = TradingStrategyFixtures.tradingStrategy(
      owner = user1
    )
    val tradingStrategy3 = TradingStrategyFixtures.tradingStrategy(
      owner = user2
    )

    tradingStrategyRepository.saveAll(
      listOf(
        tradingStrategy1,
        tradingStrategy2,
        tradingStrategy3,
      )
    )

    it("거래 전략 목록을 조회한다.") {
      val results = tradingStrategyService.findTradingStrategies()

      results.size shouldBe 3
    }
  }

  describe("findTradingStrategy") {
    val user = userRepository.save(UserFixtures.dobby())

    context("거래 전략이 존재하는 경우") {
      it("거래 전략을 반환한다.") {
        val tradingStrategy = tradingStrategyRepository.save(
          TradingStrategyFixtures.tradingStrategy(
            owner = user
          )
        )

        val result = tradingStrategyService.findTradingStrategy(tradingStrategy.id)

        result.strategyId shouldBe tradingStrategy.id
        result.strategyName shouldBe tradingStrategy.name
        result.ownerName shouldBe tradingStrategy.owner.name
        result.ownerId shouldBe tradingStrategy.owner.id
        result.visibility shouldBe tradingStrategy.visibility
      }
    }

    context("거래 전략이 존재하지 않는 경우") {
      it("TradingStrategyNotFoundException이 발생한다.") {
        shouldThrow<TradingStrategyNotFoundException> { tradingStrategyService.findTradingStrategy(-1L) }
      }
    }
  }

  describe("createTradingStrategy") {
    val user = DummyAuthUtil.dobby(userRepository)

    it("거래 전략을 생성한다.") {
      val payload = CreateTradingStrategy(
        strategyName = "Test Strategy",
        strategyDescription = "Test Description",
        strategyVisibility = TradingStrategyVisibility.PUBLIC,
      )

      tradingStrategyService.createTradingStrategy(payload)

      val results = tradingStrategyRepository.findAll()
      results.size shouldBe 1

      val result = results[0]
      result.name shouldBe "Test Strategy"
      result.description shouldBe "Test Description"
      result.visibility shouldBe TradingStrategyVisibility.PUBLIC
      result.owner.id shouldBe user.id
      result.owner.name shouldBe user.name
    }
  }
})
