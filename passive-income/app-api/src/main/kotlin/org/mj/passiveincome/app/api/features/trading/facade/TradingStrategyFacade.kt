package org.mj.passiveincome.app.api.features.trading.facade

import org.mj.passiveincome.app.api.features.stock.service.StockServiceHelper
import org.mj.passiveincome.app.api.features.trading.service.TradingStrategyServiceHelper
import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.trading.UserStrategyStock
import org.mj.passiveincome.domain.trading.UserStrategyStockRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class TradingStrategyFacade(
  private val userRepository: UserRepository,
  private val stockRepository: StockRepository,
  private val tradingStrategyRepository: TradingStrategyRepository,
  private val userStrategyStockRepository: UserStrategyStockRepository,
) {

  /**
   * 거래 전략에 주식 등록
   */
  fun registerStockToTradingStrategy(payload: RegisterUserStrategyStock) {
    val user = UserServiceHelper.findUser(userRepository, payload.userId)
    val stock = StockServiceHelper.findStock(stockRepository, payload.stockId)
    val tradingStrategy = TradingStrategyServiceHelper.findTradingStrategy(tradingStrategyRepository, payload.tradingStrategyId)

    val userStrategyStock = UserStrategyStock(
      user = user,
      stock = stock,
      tradingStrategy = tradingStrategy,
    )

    userStrategyStockRepository.save(userStrategyStock)

    // TODO: 거래 전략에 주식을 등록하면? 해당 주식 가격을 실시간으로 받아서 거래할지 말지 정해야 함
    // 이벤트 발생 시켜서 -> KIS App이 처리하도록 해야힘.
    // 이벤트 발생 시키는 방법은? -> Kafka를 사용하면 됨.
    // KIS App이 실시간으로 데이터 받아오면?
    // 해당 데이터를 가지고 거래를 할지 말지 결정하도록 해야함. -> 누가? 이건 좀 고민좀 해야겠다.
  }
}
