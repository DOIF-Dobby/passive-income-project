package org.mj.passiveincome.app.api.features.credentials.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.app.api.features.user.service.UserNotFoundException
import org.mj.passiveincome.domain.credentials.UserKisCredentials
import org.mj.passiveincome.domain.credentials.UserKisCredentialsRepository
import org.mj.passiveincome.domain.credentials.service.RegisterUserKisCredentialsService
import org.mj.passiveincome.domain.credentials.service.UserKisCredentialsAlreadyRegisteredException
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class KisCredentialsServiceTest(
  private val userRepository: UserRepository,
  private val userKisCredentialsRepository: UserKisCredentialsRepository,
) : DescribeSpec({

  val kisCredentialsService = KisCredentialsService(
    userRepository = userRepository,
    registerUserKisCredentialsService = RegisterUserKisCredentialsService(userKisCredentialsRepository)
  )

  describe("registerKisCredentials") {
    val user1 = userRepository.save(UserFixtures.user1())

    context("사용자가 존재하면") {
      it("사용자의 KIS 인증 정보를 등록한다.") {
        val payload = RegisterKisCredentials(
          userId = user1.id,
          appKey = "appKey",
          appSecretKey = "appSecretKey",
        )

        kisCredentialsService.registerKisCredentials(payload)

        val results = userKisCredentialsRepository.findAll()
        results.size shouldBe 1

        val result = results[0]
        result.user shouldBe user1
        result.appKey shouldBe "appKey"
        result.appSecretKey shouldBe "appSecretKey"
      }
    }

    context("사용자가 존재하지 않으면") {
      it("UserNotFoundException이 발생한다.") {
        val invalidPayload = RegisterKisCredentials(
          userId = -1,
          appKey = "appKey",
          appSecretKey = "appSecretKey",
        )

        shouldThrow<UserNotFoundException> { kisCredentialsService.registerKisCredentials(invalidPayload) }
      }
    }

    context("사용자한테 이미 KIS 인증 정보가 등록되어 있으면") {
      userKisCredentialsRepository.save(
        UserKisCredentials(
          user = user1,
          appKey = "appKey",
          appSecretKey = "appSecretKey",
        )
      )

      it("UserKisCredentialsAlreadyRegisteredException이 발생한다.") {
        val payload = RegisterKisCredentials(
          userId = user1.id,
          appKey = "newAppKey",
          appSecretKey = "newAppSecretKey",
        )

        shouldThrow<UserKisCredentialsAlreadyRegisteredException> { kisCredentialsService.registerKisCredentials(payload) }
      }
    }
  }
})
