package org.mj.passiveincome.domain.stock

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.common.range.MinutesRange
import org.mj.passiveincome.domain.stock.market.MarketType
import java.time.LocalTime

class MarketTypeTest : DescribeSpec({
  describe("KOSPI 시간") {
    val kospi = MarketType.KOSPI

    it("한국 시간 테스트") {
      kospi.koreanRegularMarketStartTime shouldBe LocalTime.of(9, 0)
      kospi.koreanRegularMarketEndTime shouldBe LocalTime.of(15, 30)
      kospi.koreanPreMarketStartTime shouldBe LocalTime.of(8, 30)
      kospi.koreanPreMarketEndTime shouldBe LocalTime.of(8, 40)
      kospi.koreanPostMarketStartTime shouldBe LocalTime.of(15, 40)
      kospi.koreanPostMarketEndTime shouldBe LocalTime.of(16, 0)
    }

    it("한국 시간 기준 오픈 시간 테스트") {
      kospi.koreanMarketOpenTimes shouldContainExactlyInAnyOrder listOf(
        MinutesRange.of("09:00", "15:30"),
        MinutesRange.of("08:30", "08:40"),
        MinutesRange.of("15:40", "16:00")
      )
    }

    it("오픈 체크 테스트") {
      kospi.isMarketOpen(LocalTime.of(8, 0)) shouldBe false
      kospi.isMarketOpen(LocalTime.of(8, 35)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(8, 40)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(8, 50)) shouldBe false

      kospi.isMarketOpen(LocalTime.of(9, 0)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(10, 0)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(12, 30)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(15, 30)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(15, 35)) shouldBe false

      kospi.isMarketOpen(LocalTime.of(15, 40)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(15, 50)) shouldBe true
      kospi.isMarketOpen(LocalTime.of(16, 0)) shouldBe true

      kospi.isMarketOpen(LocalTime.of(16, 10)) shouldBe false
      kospi.isMarketOpen(LocalTime.of(18, 10)) shouldBe false
    }
  }

  describe("KOSDAQ 시간") {
    val kosdaq = MarketType.KOSDAQ

    it("한국 시간 테스트") {
      kosdaq.koreanRegularMarketStartTime shouldBe LocalTime.of(9, 0)
      kosdaq.koreanRegularMarketEndTime shouldBe LocalTime.of(15, 30)
      kosdaq.koreanPreMarketStartTime shouldBe LocalTime.of(8, 30)
      kosdaq.koreanPreMarketEndTime shouldBe LocalTime.of(8, 40)
      kosdaq.koreanPostMarketStartTime shouldBe LocalTime.of(15, 40)
      kosdaq.koreanPostMarketEndTime shouldBe LocalTime.of(16, 0)
    }

    it("한국 시간 기준 오픈 시간 테스트") {
      kosdaq.koreanMarketOpenTimes shouldContainExactlyInAnyOrder listOf(
        MinutesRange.of("09:00", "15:30"),
        MinutesRange.of("08:30", "08:40"),
        MinutesRange.of("15:40", "16:00")
      )
    }

    it("오픈 체크 테스트") {
      kosdaq.isMarketOpen(LocalTime.of(8, 0)) shouldBe false
      kosdaq.isMarketOpen(LocalTime.of(8, 35)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(8, 40)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(8, 50)) shouldBe false

      kosdaq.isMarketOpen(LocalTime.of(9, 0)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(10, 0)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(12, 30)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(15, 30)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(15, 35)) shouldBe false

      kosdaq.isMarketOpen(LocalTime.of(15, 40)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(15, 50)) shouldBe true
      kosdaq.isMarketOpen(LocalTime.of(16, 0)) shouldBe true

      kosdaq.isMarketOpen(LocalTime.of(16, 10)) shouldBe false
      kosdaq.isMarketOpen(LocalTime.of(18, 10)) shouldBe false
    }
  }

  describe("NASDAQ 시간") {
    val nasdaq = MarketType.NASDAQ

    it("한국 시간 테스트") {
      nasdaq.koreanRegularMarketStartTime shouldBe LocalTime.of(23, 30)
      nasdaq.koreanRegularMarketEndTime shouldBe LocalTime.of(6, 0)
      nasdaq.koreanPreMarketStartTime shouldBe LocalTime.of(18, 0)
      nasdaq.koreanPreMarketEndTime shouldBe LocalTime.of(23, 30)
      nasdaq.koreanPostMarketStartTime shouldBe null
      nasdaq.koreanPostMarketEndTime shouldBe null
    }

    it("한국 시간 기준 오픈 시간 테스트") {
      nasdaq.koreanMarketOpenTimes shouldContainExactlyInAnyOrder listOf(
        MinutesRange.of("18:00", "23:30"),
        MinutesRange.of("23:30", "23:59"),
        MinutesRange.of("00:00", "06:00")
      )
    }

    it("오픈 체크 테스트") {
      nasdaq.isMarketOpen(LocalTime.of(18, 0)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(21, 0)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(23, 30)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(23, 50)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(0, 0)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(0, 30)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(3, 30)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(5, 30)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(6, 0)) shouldBe true
      nasdaq.isMarketOpen(LocalTime.of(7, 0)) shouldBe false
      nasdaq.isMarketOpen(LocalTime.of(9, 0)) shouldBe false
      nasdaq.isMarketOpen(LocalTime.of(12, 0)) shouldBe false
      nasdaq.isMarketOpen(LocalTime.of(15, 0)) shouldBe false
      nasdaq.isMarketOpen(LocalTime.of(17, 59)) shouldBe false
    }
  }
})
