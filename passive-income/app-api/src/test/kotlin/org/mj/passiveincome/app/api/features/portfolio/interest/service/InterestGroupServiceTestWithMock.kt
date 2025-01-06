package org.mj.passiveincome.app.api.features.portfolio.interest.service

//class InterestGroupServiceTestWithMock : DescribeSpec({
//  val interestGroupRepository = mockk<InterestGroupRepository>()
//  val userRepository = mockk<UserRepository>()
//  val interestGroupService = InterestGroupService(interestGroupRepository, userRepository)
//
//  describe("findInterestGroups") {
//    val user1 = UserFixtures.user1()
//    val user2 = UserFixtures.user2()
//
//    val mockInterestGroups = listOf(
//      InterestGroup(1L, "Group 1", user1),
//      InterestGroup(2L, "Group 2", user1),
//      InterestGroup(3L, "Group 3", user2),
//    )
//
//    every { interestGroupRepository.findAllByUserId(user1.id) } returns
//      mockInterestGroups.filter { it.user.id == user1.id }
//
//    context("사용자 ID가 주어지면") {
//      it("해당 사용자의 관심 그룹 목록을 반환한다.") {
//        val findInterestGroups = interestGroupService.findInterestGroups(user1.id)
//        findInterestGroups shouldHaveSize 2
//      }
//    }
//  }
//})
