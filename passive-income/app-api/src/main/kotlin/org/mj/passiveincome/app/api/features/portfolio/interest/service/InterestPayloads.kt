package org.mj.passiveincome.app.api.features.portfolio.interest.service

import jakarta.validation.constraints.Size
import org.mj.passiveincome.domain.portfolio.interest.InterestGroup
import org.mj.passiveincome.domain.portfolio.interest.InterestStock
import org.mj.passiveincome.domain.portfolio.interest.RecentlyViewedStock

/**
 * 관심 그룹 생성
 */
data class CreateInterestGroup(
  @field:Size(min = 2)
  val name: String,
  val userId: Long,
)

/**
 * 관심 주식 추가
 */
data class AddInterestStock(
  val stockId: Long,
  val userId: Long,
  val interestGroupId: Long
)

/**
 * 최근 본 주식 추가
 */
data class AddRecentlyViewedStock(
  val userId: Long,
  val stockId: Long,
)

/**
 * 최근 본 주식 응답
 */
data class RecentlyViewedStockResponse(
  val id: Long,
  val stockId: Long,
  val stockName: String,
) {
  companion object {
    fun of(recentlyViewedStock: RecentlyViewedStock) = recentlyViewedStock.run {
      RecentlyViewedStockResponse(
        id = id,
        stockId = stock.id,
        stockName = stock.nameKor,
      )
    }
  }
}

/**
 * 관심 그룹 응답
 */
data class InterestGroupResponse(
  val id: Long,
  val groupName: String,
  val userId: Long,
) {
  companion object {
    fun of(interestGroup: InterestGroup) = interestGroup.run {
      InterestGroupResponse(
        id = id,
        groupName = name,
        userId = user.id,
      )
    }
  }
}

/**
 * 관심 주식 응답
 */
data class InterestStockResponse(
  val id: Long,
  val stockId: Long,
  val stockName: String,
) {
  companion object {
    fun of(interestStock: InterestStock) = interestStock.run {
      InterestStockResponse(
        id = id,
        stockId = stock.id,
        stockName = stock.nameKor,
      )
    }
  }
}
