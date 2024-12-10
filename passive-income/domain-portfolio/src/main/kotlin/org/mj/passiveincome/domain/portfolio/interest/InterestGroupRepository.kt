package org.mj.passiveincome.domain.portfolio.interest

import org.springframework.data.jpa.repository.JpaRepository

interface InterestGroupRepository : JpaRepository<InterestGroup, Long> {

  fun findAllByUserId(userId: Long): List<InterestGroup>
}
