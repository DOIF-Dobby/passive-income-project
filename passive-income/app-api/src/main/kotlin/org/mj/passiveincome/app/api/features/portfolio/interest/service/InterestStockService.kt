package org.mj.passiveincome.app.api.features.portfolio.interest.service

import org.mj.passiveincome.app.api.features.stock.service.StockServiceHelper
import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.portfolio.interest.InterestGroupRepository
import org.mj.passiveincome.domain.portfolio.interest.InterestStock
import org.mj.passiveincome.domain.portfolio.interest.InterestStockRepository
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class InterestStockService(
  private val interestStockRepository: InterestStockRepository,
  private val interestGroupRepository: InterestGroupRepository,
  private val stockRepository: StockRepository,
  private val userRepository: UserRepository,
) {

  /**
   * 관심 주식 추가
   */
  fun addInterestStock(payload: AddInterestStock) {
    val interestStock = InterestStock(
      group = InterestServiceHelper.findInterestGroup(interestGroupRepository, payload.interestGroupId),
      stock = StockServiceHelper.findStock(stockRepository, payload.stockId),
      user = UserServiceHelper.findUser(userRepository, payload.userId),
    )

    interestStockRepository.save(interestStock)
  }

  /**
   * 사용자 / 그룹 별 관심 주식 목록 조회
   */
  fun findInterestStocks(userId: Long, groupId: Long): List<InterestStockResponse> {
    return interestStockRepository.findInterestStocks(
      userId = userId,
      groupId = groupId,
    ).map { InterestStockResponse.of(it) }
  }
}
