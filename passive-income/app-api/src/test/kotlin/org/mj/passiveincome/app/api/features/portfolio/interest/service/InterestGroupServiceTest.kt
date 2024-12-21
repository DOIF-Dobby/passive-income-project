package org.mj.passiveincome.app.api.features.portfolio.interest.service

import io.kotest.assertions.extracting
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
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
) : DescribeSpec({

  val interestGroupService = InterestGroupService(interestGroupRepository, userRepository)

  describe("createInterestGroup") {
    val user = userRepository.save(User(username = "Test User1"))
    val payload = CreateInterestGroup(name = "Group 1", userId = user.id)

    context("관심 그룹 생성 요청을 받으면") {
      it("관심 그룹을 생성한다.") {
        interestGroupService.createInterestGroup(payload)

        val results = interestGroupRepository.findAll()
        results shouldHaveSize 1

        val result = results[0]
        result.name shouldBe "Group 1"
        result.user shouldBe user
      }
    }
  }

  describe("findInterestGroups") {
    val user1 = userRepository.save(User(username = "Test User1"))
    val user2 = userRepository.save(User(username = "Test User2"))

    interestGroupRepository.saveAll(
      listOf(
        InterestGroup(name = "Group 1", user = user1),
        InterestGroup(name = "Group 2", user = user1),
        InterestGroup(name = "Group 3", user = user2),
      )
    )

    context("사용자 ID가 주어지면") {
      it("해당 사용자의 관심 그룹 목록을 반환한다.") {
        val results = interestGroupService.findInterestGroups(user1.id)
        results shouldHaveSize 2
        extracting(results) { groupName } shouldBe listOf("Group 1", "Group 2")
        extracting(results) { userId } shouldBe listOf(user1.id, user1.id)
      }
    }
  }

})
