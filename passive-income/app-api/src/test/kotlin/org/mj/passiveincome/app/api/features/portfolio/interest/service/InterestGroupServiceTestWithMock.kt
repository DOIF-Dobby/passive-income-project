package org.mj.passiveincome.app.api.features.portfolio.interest.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.every
import io.mockk.mockk
import org.mj.passiveincome.domain.portfolio.interest.InterestGroup
import org.mj.passiveincome.domain.portfolio.interest.InterestGroupRepository
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository

class InterestGroupServiceTestWithMock : DescribeSpec({
  val interestGroupRepository = mockk<InterestGroupRepository>()
  val userRepository = mockk<UserRepository>()
  val interestGroupService = InterestGroupService(interestGroupRepository, userRepository)

  describe("findInterestGroups") {
    val userId = 1L
    val user1 = User(1L, "Test User1")
    val user2 = User(2L, "Test User2")

    val mockInterestGroups = listOf(
      InterestGroup(1L, "Group 1", user1),
      InterestGroup(2L, "Group 2", user1),
      InterestGroup(3L, "Group 3", user2),
    )

    every { interestGroupRepository.findAllByUserId(userId) } returns
      mockInterestGroups.filter { it.user.id == userId }

    context("사용자 ID가 주어지면") {
      it("해당 사용자의 관심 그룹 목록을 반환한다.") {
        val findInterestGroups = interestGroupService.findInterestGroups(userId)
        findInterestGroups shouldHaveSize 2
      }
    }
  }
})
