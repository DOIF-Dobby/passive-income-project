package org.mj.passiveincome.domain.portfolio.service

import org.mj.passiveincome.domain.portfolio.interest.InterestStock
import org.mj.passiveincome.domain.portfolio.interest.InterestStockRepository
import org.springframework.stereotype.Service

@Service
class InterestStockDomainService(
  private val interestStockRepository: InterestStockRepository,
) {

  /**
   * 관심 주식 추가
   */
  fun addInterestStock(entity: InterestStock): InterestStock {
    val duplicateStockInGroup = interestStockRepository.isDuplicateStockInGroup(
      userId = entity.user.id,
      groupId = entity.group.id,
      stockId = entity.stock.id,
    )

    // 이미 그룹에 추가된 주식인 경우 중복으로 추가할 수 없다.
    if (duplicateStockInGroup) {
      throw DuplicateStockInGroupException()
    }

    return interestStockRepository.save(entity)
  }

}
