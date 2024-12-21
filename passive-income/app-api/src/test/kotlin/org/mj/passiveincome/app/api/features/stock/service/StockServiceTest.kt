package org.mj.passiveincome.app.api.features.stock.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.stock.SecurityType
import org.mj.passiveincome.domain.stock.StockFixtures
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.stock.StockType
import org.mj.passiveincome.domain.stock.market.MarketType
import java.time.LocalDate

@AppApiDataJpaTest
class StockServiceTest(
  val stockRepository: StockRepository,
) : DescribeSpec({

  val stockService = StockService(stockRepository)

  describe("findStocks") {
    stockRepository.saveAll(StockFixtures.defaultStocks())

    it("주식 목록을 반환한다.") {
      val results = stockService.findStocks()
      results shouldHaveSize 3
    }
  }

  describe("findStock") {
    val savedStocks = stockRepository.saveAll(StockFixtures.defaultStocks())

    context("주식 Entity ID가 주어지면") {
      val findStockId = savedStocks[0].id

      it("해당하는 주식을 반환한다.") {
        val result = stockService.findStock(findStockId)

        result.stockId shouldBe findStockId
        result.standardCode shouldBe "KR7005930003"
        result.shortCode shouldBe "005930"
        result.nameKor shouldBe "삼성전자보통주"
        result.shortNameKor shouldBe "삼성전자"
        result.nameEng shouldBe "SamsungElectronics"
        result.listingDate shouldBe LocalDate.of(1975, 6, 11)
        result.marketType shouldBe MarketType.KOSPI
        result.securityType shouldBe SecurityType.EQUITY
        result.stockType shouldBe StockType.COMMON
      }
    }

    context("존재 하지 않는 주식 Entity ID가 주어지면") {
      it("StockNotFoundException이 발생한다.") {
        shouldThrow<StockNotFoundException> {
          stockService.findStock(9999)
        }
      }
    }
  }

  describe("registerStock") {
    val payload = RegisterStock(
      standardCode = "KR7000660001",
      shortCode = "000660",
      nameKor = "에스케이하이닉스보통주",
      shortNameKor = "SK하이닉스",
      nameEng = "SK hynix",
      listingDate = LocalDate.of(1996, 12, 26),
      marketType = MarketType.KOSPI,
      securityType = SecurityType.EQUITY,
      stockType = StockType.COMMON,
    )

    it("주식을 등록한다.") {
      stockService.registerStock(payload)

      val results = stockRepository.findAll()
      results shouldHaveSize 1
    }
  }
})
