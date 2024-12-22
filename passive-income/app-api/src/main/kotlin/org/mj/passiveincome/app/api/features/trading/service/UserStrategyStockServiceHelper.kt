package org.mj.passiveincome.app.api.features.trading.service

import org.mj.passiveincome.domain.trading.UserStrategyStock
import org.mj.passiveincome.domain.trading.UserStrategyStockRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.mj.passiveincome.system.web.exception.NotFoundApiException

class UserStrategyStockNotFoundException : NotFoundApiException(messageProperty = "user-strategy-stock.not-found")

class UserStrategyStockServiceHelper {

  companion object {
    /**
     * 사용자 전략 주식 조회
     */
    fun findUserStrategyStock(repository: UserStrategyStockRepository, userStrategyStockId: Long): UserStrategyStock {
      return repository.findByIdOrThrow(userStrategyStockId) { throw UserStrategyStockNotFoundException() }
    }
  }
}
