package org.mj.passiveincome.app.api.features.user.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class UserServiceTest(
  private val userRepository: UserRepository,
) : DescribeSpec({

  val userService = UserService(userRepository)

  describe("findUser") {
    val user = userRepository.save(UserFixtures.dobby())

    context("사용자 ID에 해당하는 사용자가 존재하면") {
      it("사용자 정보를 반환한다.") {
        val result = userService.findUser(user.id)

        result.id shouldBe user.id
        result.username shouldBe user.name
      }
    }

    context("사용자 ID에 해당하는 사용자가 존재하지 않으면") {
      it("UserNotFoundException이 발생한다.") {
        shouldThrow<UserNotFoundException> { userService.findUser(-1) }
      }
    }
  }
})
