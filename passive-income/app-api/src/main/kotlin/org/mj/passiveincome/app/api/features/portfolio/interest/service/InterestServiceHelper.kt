package org.mj.passiveincome.app.api.features.portfolio.interest.service

import org.mj.passiveincome.domain.portfolio.interest.InterestGroup
import org.mj.passiveincome.domain.portfolio.interest.InterestGroupRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.mj.passiveincome.system.web.exception.NotFoundApiException

class InterestGroupNotFoundException : NotFoundApiException(messageProperty = "interest-group.not-found")

class InterestServiceHelper {

  companion object {
    fun findInterestGroup(repository: InterestGroupRepository, groupId: Long): InterestGroup {
      return repository.findByIdOrThrow(groupId) { throw InterestGroupNotFoundException() }
    }
  }
}
