package org.mj.passiveincome.app.api.features.user.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.account.UserAccountRepository
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class UserAccountServiceTest(
  private val userRepository: UserRepository,
  private val userAccountRepository: UserAccountRepository
) : DescribeSpec({

  val userAccountService = UserAccountService(userRepository, userAccountRepository)

  describe("registerAccount") {
    val user = userRepository.save(UserFixtures.user1())

    context("사용자 ID에 해당하는 사용자가 존재하면") {
      it("해당 사용자의 계좌를 등록한다.") {

        val payload = RegisterAccount(
          userId = user.id,
          accountNumber = "1234567890",
        )

        userAccountService.registerAccount(payload)

        val results = userAccountRepository.findAll()
        results.size shouldBe 1

        val result = results[0]
        result.user.id shouldBe user.id
        result.accountNumber shouldBe "1234567890"
      }
    }

    context("사용자 ID에 해당하는 사용자가 존재하지 않으면") {
      it("UserNotFoundException이 발생한다.") {
        val invalidPayload = RegisterAccount(
          userId = -1,
          accountNumber = "1234567890",
        )

        shouldThrow<UserNotFoundException> { userAccountService.registerAccount(invalidPayload) }
      }
    }
  }
})
