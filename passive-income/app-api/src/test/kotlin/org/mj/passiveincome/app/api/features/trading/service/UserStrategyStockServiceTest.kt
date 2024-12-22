package org.mj.passiveincome.app.api.features.trading.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.app.api.features.stock.service.StockNotFoundException
import org.mj.passiveincome.app.api.features.user.service.UserNotFoundException
import org.mj.passiveincome.domain.stock.StockFixtures
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.trading.TradingActivateStatus
import org.mj.passiveincome.domain.trading.TradingStrategyFixtures
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.trading.UserStrategyStock
import org.mj.passiveincome.domain.trading.UserStrategyStockRepository
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class UserStrategyStockServiceTest(
  private val userRepository: UserRepository,
  private val stockRepository: StockRepository,
  private val tradingStrategyRepository: TradingStrategyRepository,
  private val userStrategyStockRepository: UserStrategyStockRepository,
) : DescribeSpec({

  val userStrategyStockService = UserStrategyStockService(
    userRepository,
    stockRepository,
    tradingStrategyRepository,
    userStrategyStockRepository,
  )

  describe("registerStockToTradingStrategy") {
    val user = userRepository.save(UserFixtures.user1())
    val stock = stockRepository.save(StockFixtures.stockSamsung())
    val tradingStrategy = tradingStrategyRepository.save(TradingStrategyFixtures.tradingStrategy1())

    context("사용자 / 주식 / 거래 전략이 모두 존재하는 경우") {
      it("사용자 거래 전략에 해당 주식을 등록한다.") {
        val payload = RegisterUserStrategyStock(
          userId = user.id,
          stockId = stock.id,
          tradingStrategyId = tradingStrategy.id,
        )

        userStrategyStockService.registerStockToTradingStrategy(payload)

        val results = userStrategyStockRepository.findAll()
        results.size shouldBe 1

        val result = results[0]
        result.user shouldBe user
        result.stock shouldBe stock
        result.tradingStrategy shouldBe tradingStrategy
        result.tradingActivateStatus shouldBe TradingActivateStatus.INACTIVE
      }
    }

    context("사용자가 존재하지 않는 경우") {
      it("UserNotFoundException이 발생한다.") {
        val payload = RegisterUserStrategyStock(
          userId = -1,
          stockId = stock.id,
          tradingStrategyId = tradingStrategy.id,
        )

        shouldThrow<UserNotFoundException> { userStrategyStockService.registerStockToTradingStrategy(payload) }
      }
    }

    context("주식이 존재하지 않는 경우") {
      it("StockNotFoundException이 발생한다.") {
        val payload = RegisterUserStrategyStock(
          userId = user.id,
          stockId = -1,
          tradingStrategyId = tradingStrategy.id,
        )

        shouldThrow<StockNotFoundException> { userStrategyStockService.registerStockToTradingStrategy(payload) }
      }
    }

    context("거래 전략이 존재하지 않는 경우") {
      it("TradingStrategyNotFoundException이 발생한다.") {
        val payload = RegisterUserStrategyStock(
          userId = user.id,
          stockId = stock.id,
          tradingStrategyId = -1,
        )

        shouldThrow<TradingStrategyNotFoundException> { userStrategyStockService.registerStockToTradingStrategy(payload) }
      }
    }
  }

  describe("activateTradingStrategy") {
    val user = userRepository.save(UserFixtures.user1())
    val stock = stockRepository.save(StockFixtures.stockSamsung())
    val tradingStrategy = tradingStrategyRepository.save(TradingStrategyFixtures.tradingStrategy1())
    val userStrategyStock = userStrategyStockRepository.save(
      UserStrategyStock(
        user = user,
        stock = stock,
        tradingStrategy = tradingStrategy,
      )
    )

    context("해당 Entity가 존재하는 경우") {
      it("거래 전략 주식을 활성화한다.") {
        userStrategyStockService.activateTradingStrategy(userStrategyStock.id)

        val result = userStrategyStockRepository.findById(userStrategyStock.id).get()
        result.tradingActivateStatus shouldBe TradingActivateStatus.ACTIVE
      }
    }

    context("해당 Entity가 존재하지 않는 경우") {
      it("UserStrategyStockNotFoundException이 발생한다.") {
        shouldThrow<UserStrategyStockNotFoundException> { userStrategyStockService.activateTradingStrategy(-1) }
      }
    }
  }

  describe("deactivateTradingStrategy") {
    val user = userRepository.save(UserFixtures.user1())
    val stock = stockRepository.save(StockFixtures.stockSamsung())
    val tradingStrategy = tradingStrategyRepository.save(TradingStrategyFixtures.tradingStrategy1())

    val userStrategyStock = UserStrategyStock(
      user = user,
      stock = stock,
      tradingStrategy = tradingStrategy,
    )
    userStrategyStock.activate()
    userStrategyStockRepository.save(userStrategyStock)

    context("해당 Entity가 존재하는 경우") {
      it("거래 전략 주식을 비활성화한다.") {
        userStrategyStock.tradingActivateStatus shouldBe TradingActivateStatus.ACTIVE

        userStrategyStockService.deactivateTradingStrategy(userStrategyStock.id)

        val result = userStrategyStockRepository.findById(userStrategyStock.id).get()
        result.tradingActivateStatus shouldBe TradingActivateStatus.INACTIVE
      }
    }

    context("해당 Entity가 존재하지 않는 경우") {
      it("UserStrategyStockNotFoundException이 발생한다.") {
        shouldThrow<UserStrategyStockNotFoundException> { userStrategyStockService.deactivateTradingStrategy(-1) }
      }
    }
  }
})
