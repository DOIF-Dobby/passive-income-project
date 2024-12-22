package org.mj.passiveincome.app.api.features.portfolio.interest.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.app.api.features.stock.service.StockNotFoundException
import org.mj.passiveincome.app.api.features.user.service.UserNotFoundException
import org.mj.passiveincome.domain.portfolio.interest.RecentlyViewedStock
import org.mj.passiveincome.domain.portfolio.interest.RecentlyViewedStockRepository
import org.mj.passiveincome.domain.stock.StockFixtures
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class RecentlyViewedStockServiceTest(
  private val recentlyViewedStockRepository: RecentlyViewedStockRepository,
  private val userRepository: UserRepository,
  private val stockRepository: StockRepository,
) : DescribeSpec({

  val recentlyViewedStockService = RecentlyViewedStockService(recentlyViewedStockRepository, userRepository, stockRepository)

  describe("addRecentlyViewedStock") {
    val user1 = userRepository.save(UserFixtures.user1())
    val stockSamsung = stockRepository.save(StockFixtures.stockSamsung())


    context("사용자와 주식이 모두 존재하는 경우") {
      it("사용자의 최근 본 주식에 해당 주식을 추가한다.") {
        recentlyViewedStockService.addRecentlyViewedStock(AddRecentlyViewedStock(user1.id, stockSamsung.id))

        val results = recentlyViewedStockRepository.findAll()
        results.size shouldBe 1

        val result = results[0]
        result.user shouldBe user1
        result.stock shouldBe stockSamsung
      }
    }

    context("사용자가 없는 경우") {
      it("UserNotFoundException이 발생한다.") {
        shouldThrow<UserNotFoundException> {
          recentlyViewedStockService.addRecentlyViewedStock(AddRecentlyViewedStock(-1, stockSamsung.id))
        }
      }
    }

    context("주식이 없는 경우") {
      it("StockNotFoundException이 발생한다.") {
        shouldThrow<StockNotFoundException> {
          recentlyViewedStockService.addRecentlyViewedStock(AddRecentlyViewedStock(user1.id, -1))
        }
      }
    }
  }

  describe("findRecentlyViewedStocks") {
    val user1 = userRepository.save(UserFixtures.user1())
    val user2 = userRepository.save(UserFixtures.user2())
    val stocks = stockRepository.saveAll(StockFixtures.stocks())

    stocks.forEach {
      recentlyViewedStockRepository.save(RecentlyViewedStock(user = user1, stock = it))
    }

    context("사용자 ID에 해당하는 사용자가 최근 본 주식이 있는 경우") {
      it("사용자의 최근 본 주식 목록을 반환한다.") {
        val results = recentlyViewedStockService.findRecentlyViewedStocks(user1.id)
        results.size shouldBe 3

        val stockSamsung = results.find { it.stockId == stocks[0].id }!!

        stockSamsung.stockName shouldBe "삼성전자보통주"
        stockSamsung.stockShortName shouldBe "삼성전자"
      }
    }

    context("사용자 ID에 해당하는 사용자가 최근 본 주식이 없는 경우") {
      it("빈 목록을 반환한다.") {
        val results = recentlyViewedStockService.findRecentlyViewedStocks(user2.id)
        results.size shouldBe 0
      }
    }

    context("사용자 ID에 해당하는 사용자가 없는 경우") {
      it("빈 목록을 반환한다.") {
        val results = recentlyViewedStockService.findRecentlyViewedStocks(-1)
        results.size shouldBe 0
      }
    }
  }
})
