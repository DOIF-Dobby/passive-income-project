package org.mj.passiveincome.app.service.stock

import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.system.core.extension.findAllWithMap
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
  private val stockRepository: StockRepository
) {

  @Transactional(readOnly = true)
  fun findStocks(): List<StockResponse> {
    return stockRepository.findAllWithMap { StockResponse.of(it) }
  }
}
