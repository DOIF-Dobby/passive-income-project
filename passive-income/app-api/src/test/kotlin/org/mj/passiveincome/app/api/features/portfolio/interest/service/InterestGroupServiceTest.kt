package org.mj.passiveincome.app.api.features.portfolio.interest.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import org.mj.passiveincome.app.api.config.JpaConfig
import org.mj.passiveincome.domain.portfolio.interest.InterestGroup
import org.mj.passiveincome.domain.portfolio.interest.InterestGroupRepository
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(JpaConfig::class)
class InterestGroupServiceTest(
  val interestGroupRepository: InterestGroupRepository,
  val userRepository: UserRepository,
) : DescribeSpec() {

  override fun extensions() = listOf(SpringExtension)

  private val interestGroupService = InterestGroupService(interestGroupRepository, userRepository)

  init {
    beforeEach {
      val user1 = userRepository.save(User(username = "Test User1"))
      val user2 = userRepository.save(User(username = "Test User2"))

      interestGroupRepository.saveAll(
        listOf(
          InterestGroup(name = "Group 1", user = user1),
          InterestGroup(name = "Group 2", user = user1),
          InterestGroup(name = "Group 3", user = user2),
        )
      )
    }

    this.describe("findInterestGroups") {
      context("사용자 ID가 주어지면") {
        it("해당 사용자의 관심 그룹 목록을 반환한다.") {
          val findInterestGroups = interestGroupService.findInterestGroups(1L)
          findInterestGroups shouldHaveSize 2
        }
      }
    }
  }
}
