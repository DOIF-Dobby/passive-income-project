package org.mj.passiveincome.app.api.features.portfolio.interest.service

import org.mj.passiveincome.app.api.features.stock.service.StockServiceHelper
import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.portfolio.interest.RecentlyViewedStock
import org.mj.passiveincome.domain.portfolio.interest.RecentlyViewedStockRepository
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecentlyViewedStockService(
  private val recentlyViewedStockRepository: RecentlyViewedStockRepository,
  private val userRepository: UserRepository,
  private val stockRepository: StockRepository,
) {

  /**
   * 최근 본 주식 추가
   */
  @Transactional
  fun addRecentlyViewedStock(payload: AddRecentlyViewedStock) {
    val recentlyViewedStock = RecentlyViewedStock(
      user = UserServiceHelper.findUser(userRepository, payload.userId),
      stock = StockServiceHelper.findStock(stockRepository, payload.stockId),
    )

    recentlyViewedStockRepository.save(recentlyViewedStock)
  }

  /**
   * 사용자 최근 본 주식 목록 조회
   */
  @Transactional(readOnly = true)
  fun findRecentlyViewedStocks(userId: Long): List<RecentlyViewedStockResponse> {
    return recentlyViewedStockRepository.findRecentlyViewedStocks(userId)
      .map { RecentlyViewedStockResponse.of(it) }
  }
}
