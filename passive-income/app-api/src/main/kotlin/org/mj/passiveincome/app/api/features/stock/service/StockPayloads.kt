package org.mj.passiveincome.app.api.features.stock.service

import org.mj.passiveincome.domain.stock.SecurityType
import org.mj.passiveincome.domain.stock.Stock
import org.mj.passiveincome.domain.stock.StockType
import org.mj.passiveincome.domain.stock.market.MarketType
import java.time.LocalDate

data class RegisterStock(
  val standardCode: String,
  val shortCode: String,
  val nameKor: String,
  val shortNameKor: String,
  val nameEng: String,
  val listingDate: LocalDate,
  val marketType: MarketType,
  val securityType: SecurityType,
  val stockType: StockType,
)

data class StockResponse(
  val stockId: Long,
  val standardCode: String,
  val shortCode: String,
  val nameKor: String,
  val shortNameKor: String,
  val nameEng: String,
  val listingDate: LocalDate,
  val marketType: MarketType,
  val securityType: SecurityType,
  val securityTypeName: String,
  val stockType: StockType,
  val stockTypeName: String,
) {
  companion object {
    fun of(stock: Stock) = stock.run {
      StockResponse(
        stockId = id,
        standardCode = standardCode,
        shortCode = shortCode,
        nameKor = nameKor,
        shortNameKor = shortNameKor,
        nameEng = nameEng,
        listingDate = listingDate,
        marketType = marketType,
        securityType = securityType,
        securityTypeName = securityType.typeName,
        stockType = stockType,
        stockTypeName = stockType.typeName,
      )
    }
  }
}
