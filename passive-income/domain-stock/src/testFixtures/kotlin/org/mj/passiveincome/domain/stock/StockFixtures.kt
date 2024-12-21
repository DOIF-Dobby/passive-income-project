package org.mj.passiveincome.domain.stock

import org.mj.passiveincome.domain.stock.market.MarketType
import java.time.LocalDate

class StockFixtures {
  companion object {
    fun stockSamsung() = Stock(
      standardCode = "KR7005930003",
      shortCode = "005930",
      nameKor = "삼성전자보통주",
      shortNameKor = "삼성전자",
      nameEng = "SamsungElectronics",
      listingDate = LocalDate.of(1975, 6, 11),
      marketType = MarketType.KOSPI,
      securityType = SecurityType.EQUITY,
      stockType = StockType.COMMON,
    )

    fun stockNaver() = Stock(
      standardCode = "KR7035420009",
      shortCode = "035420",
      nameKor = "NAVER보통주",
      shortNameKor = "NAVER",
      nameEng = "NAVER",
      listingDate = LocalDate.of(2008, 11, 28),
      marketType = MarketType.KOSPI,
      securityType = SecurityType.EQUITY,
      stockType = StockType.COMMON,
    )

    fun stockKakao() = Stock(
      standardCode = "KR7035720002",
      shortCode = "035720",
      nameKor = "카카오보통주",
      shortNameKor = "카카오",
      nameEng = "Kakao",
      listingDate = LocalDate.of(2017, 7, 10),
      marketType = MarketType.KOSPI,
      securityType = SecurityType.EQUITY,
      stockType = StockType.COMMON,
    )

    fun defaultStocks() = listOf(stockSamsung(), stockNaver(), stockKakao())
  }
}
