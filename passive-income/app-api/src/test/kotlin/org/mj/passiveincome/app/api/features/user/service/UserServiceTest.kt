package org.mj.passiveincome.app.api.features.user.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.domain.user.UserStatus

@AppApiDataJpaTest
class UserServiceTest(
  private val userRepository: UserRepository,
) : DescribeSpec({

  val userService = UserService(userRepository)

  describe("findUser") {
    val user = userRepository.save(User(name = "Test User1"))

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

  describe("signUp") {
    val payload = CreateUser(
      username = "Test User1",
    )

    it("사용자를 등록한다.") {
      userService.signUp(payload)

      val results = userRepository.findAll()
      results.size shouldBe 1

      val result = results[0]
      result.name shouldBe "Test User1"
      result.status shouldBe UserStatus.ACTIVE
    }
  }
})
