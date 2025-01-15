package org.mj.passiveincome.app.api.features.portfolio.investment.service

import org.mj.passiveincome.domain.portfolio.investment.UserInvestment
import org.mj.passiveincome.domain.portfolio.investment.UserInvestmentRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.mj.passiveincome.system.web.exception.NotFoundApiException

class UserInvestmentNotFoundException : NotFoundApiException(messageProperty = "user-investment.not-found")

class UserInvestmentServiceHelper {

  companion object {
    /**
     * 사용자 투자 조회
     */
    fun findUserInvestment(repository: UserInvestmentRepository, userInvestmentId: Long): UserInvestment {
      return repository.findByIdOrThrow(userInvestmentId) { throw UserInvestmentNotFoundException() }
    }
  }
}
