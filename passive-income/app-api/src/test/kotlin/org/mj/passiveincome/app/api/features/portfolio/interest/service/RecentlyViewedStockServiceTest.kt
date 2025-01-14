package org.mj.passiveincome.app.api.features.portfolio.interest.service

import AppApiDataJpaTest
import DummyAuthUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.app.api.features.stock.service.StockNotFoundException
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
    val user = DummyAuthUtil.dobby(userRepository)
    val stockSamsung = stockRepository.save(StockFixtures.stockSamsung())


    context("사용자와 주식이 모두 존재하는 경우") {
      it("사용자의 최근 본 주식에 해당 주식을 추가한다.") {
        recentlyViewedStockService.addRecentlyViewedStock(AddRecentlyViewedStock(stockSamsung.id))

        val results = recentlyViewedStockRepository.findAll()
        results.size shouldBe 1

        val result = results[0]
        result.user shouldBe user
        result.stock shouldBe stockSamsung
      }
    }

    context("주식이 없는 경우") {
      it("StockNotFoundException이 발생한다.") {
        shouldThrow<StockNotFoundException> {
          recentlyViewedStockService.addRecentlyViewedStock(AddRecentlyViewedStock(-1))
        }
      }
    }
  }

  describe("findRecentlyViewedStocks") {
    val user1 = DummyAuthUtil.dobby(userRepository)
    val user2 = userRepository.save(UserFixtures.myungJin())
    val stocks = stockRepository.saveAll(StockFixtures.stocks())

    stocks.forEach {
      recentlyViewedStockRepository.save(RecentlyViewedStock(user = user1, stock = it))
    }

    context("사용자 ID에 해당하는 사용자가 최근 본 주식이 있는 경우") {
      it("사용자의 최근 본 주식 목록을 반환한다.") {
        val results = recentlyViewedStockService.findRecentlyViewedStocks()
        results.size shouldBe 3

        val stockSamsung = results.find { it.stockId == stocks[0].id }!!

        stockSamsung.stockName shouldBe "삼성전자보통주"
        stockSamsung.stockShortName shouldBe "삼성전자"
      }
    }
  }
})
