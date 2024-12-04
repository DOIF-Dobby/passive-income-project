package org.mj.passiveincome.app.api.features.stock.service

import org.mj.passiveincome.domain.stock.Stock
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.mj.passiveincome.system.web.exception.NotFoundApiException

class StockNotFoundException : NotFoundApiException(message = "주식을 찾을 수 없습니다.")

class StockServiceHelper {

  companion object {
    fun findStock(repository: StockRepository, stockId: Long): Stock {
      return repository.findByIdOrThrow(stockId) { throw StockNotFoundException() }
    }
  }
}
