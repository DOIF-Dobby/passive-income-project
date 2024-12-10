package org.mj.passiveincome.app.api.features.portfolio.interest.service

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class InterestGroupServiceTest {

//  private val interestGroupService = InterestGroupService(
//    interestGroupRepository = interestGroupRepository,
//    userRepository = userRepository,
//  )

  private lateinit var interestGroupService: InterestGroupService

  @Test
  @DisplayName("관심 그룹 생성 테스트")
  fun createInterestGroupTest() {
    // given
    interestGroupService.findInterestGroups(1L)

    // when

    // then
  }
}
