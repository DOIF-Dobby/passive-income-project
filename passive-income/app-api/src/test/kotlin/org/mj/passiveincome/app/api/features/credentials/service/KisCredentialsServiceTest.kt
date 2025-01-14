package org.mj.passiveincome.app.api.features.credentials.service

import AppApiDataJpaTest
import DummyAuthUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.credentials.UserKisCredentials
import org.mj.passiveincome.domain.credentials.UserKisCredentialsRepository
import org.mj.passiveincome.domain.credentials.service.RegisterUserKisCredentialsService
import org.mj.passiveincome.domain.credentials.service.UserKisCredentialsAlreadyRegisteredException
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
    val user = DummyAuthUtil.dobby(userRepository)

    context("사용자가 존재하면") {
      it("사용자의 KIS 인증 정보를 등록한다.") {
        val payload = RegisterKisCredentials(
          appKey = "appKey",
          appSecretKey = "appSecretKey",
        )

        kisCredentialsService.registerKisCredentials(payload)

        val results = userKisCredentialsRepository.findAll()
        results.size shouldBe 1

        val result = results[0]
        result.user shouldBe user
        result.appKey shouldBe "appKey"
        result.appSecretKey shouldBe "appSecretKey"
      }
    }

    context("사용자한테 이미 KIS 인증 정보가 등록되어 있으면") {
      userKisCredentialsRepository.save(
        UserKisCredentials(
          user = user,
          appKey = "appKey",
          appSecretKey = "appSecretKey",
        )
      )

      it("UserKisCredentialsAlreadyRegisteredException이 발생한다.") {
        val payload = RegisterKisCredentials(
          appKey = "newAppKey",
          appSecretKey = "newAppSecretKey",
        )

        shouldThrow<UserKisCredentialsAlreadyRegisteredException> { kisCredentialsService.registerKisCredentials(payload) }
      }
    }
  }
})
