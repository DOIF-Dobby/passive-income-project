package org.mj.passiveincome.app.api.features.stock.service

import org.mj.passiveincome.domain.stock.Stock
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.system.data.findAllWithMap
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
  private val stockRepository: StockRepository
) {

  /**
   * 주식 목록 조회
   */
  @Transactional(readOnly = true)
  fun findStocks(): List<StockResponse> {
    return stockRepository.findAllWithMap { StockResponse.of(it) }
  }

  /**
   * 주식 조회
   */
  @Transactional(readOnly = true)
  fun findStock(stockId: Long): StockResponse {
    val stock = StockServiceHelper.findStock(stockRepository, stockId)
    return StockResponse.of(stock)
  }

  /**
   * 주식 등록
   */
  @Transactional
  fun registerStock(payload: RegisterStock) {
    val stock = payload.run {
      Stock(
        standardCode = standardCode,
        shortCode = shortCode,
        nameKor = nameKor,
        shortNameKor = shortNameKor,
        nameEng = nameEng,
        listingDate = listingDate,
        marketType = marketType,
        securityType = securityType,
        stockType = stockType,
      )
    }

    stockRepository.save(stock)
  }
}
