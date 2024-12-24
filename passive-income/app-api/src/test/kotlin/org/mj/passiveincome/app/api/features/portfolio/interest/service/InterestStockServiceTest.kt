package org.mj.passiveincome.app.api.features.portfolio.interest.service

import AppApiDataJpaTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.app.api.features.stock.service.StockNotFoundException
import org.mj.passiveincome.app.api.features.user.service.UserNotFoundException
import org.mj.passiveincome.domain.portfolio.interest.InterestGroup
import org.mj.passiveincome.domain.portfolio.interest.InterestGroupRepository
import org.mj.passiveincome.domain.portfolio.interest.InterestStock
import org.mj.passiveincome.domain.portfolio.interest.InterestStockRepository
import org.mj.passiveincome.domain.portfolio.interest.service.AddInterestStockService
import org.mj.passiveincome.domain.portfolio.interest.service.DuplicateStockInGroupException
import org.mj.passiveincome.domain.stock.StockFixtures
import org.mj.passiveincome.domain.stock.StockRepository
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class InterestStockServiceTest(
  private val interestStockRepository: InterestStockRepository,
  private val interestGroupRepository: InterestGroupRepository,
  private val stockRepository: StockRepository,
  private val userRepository: UserRepository,
) : DescribeSpec({

  val interestStockService = InterestStockService(
    interestStockRepository,
    interestGroupRepository,
    stockRepository,
    userRepository,
    AddInterestStockService(interestStockRepository),
  )

  describe("addInterestStock") {
    val user1 = userRepository.save(UserFixtures.user1())
    val stockSamsung = stockRepository.save(StockFixtures.stockSamsung())
    val interestGroup = interestGroupRepository.save(InterestGroup(name = "Group 1", user = user1))

    context("사용자 / 주식 / 관심 그룹이 모두 존재하는 경우") {
      it("사용자의 관심 주식에 해당 주식을 추가한다.") {
        val payload = AddInterestStock(
          userId = user1.id,
          stockId = stockSamsung.id,
          interestGroupId = interestGroup.id,
        )

        interestStockService.addInterestStock(payload)

        val results = interestStockRepository.findAll()
        results.size shouldBe 1

        val result = results[0]
        result.user shouldBe user1
        result.stock shouldBe stockSamsung
        result.group shouldBe interestGroup

      }
    }

    context("사용자가 없는 경우") {
      it("UserNotFoundException이 발생한다.") {
        val invalidPayload = AddInterestStock(
          userId = -1,
          stockId = stockSamsung.id,
          interestGroupId = interestGroup.id,
        )

        shouldThrow<UserNotFoundException> {
          interestStockService.addInterestStock(invalidPayload)
        }
      }
    }

    context("주식이 없는 경우") {
      it("StockNotFoundException이 발생한다.") {
        val invalidPayload = AddInterestStock(
          userId = user1.id,
          stockId = -1,
          interestGroupId = interestGroup.id,
        )

        shouldThrow<StockNotFoundException> {
          interestStockService.addInterestStock(invalidPayload)
        }
      }
    }

    context("관심 그룹이 없는 경우") {
      it("InterestGroupNotFoundException이 발생한다.") {
        val invalidPayload = AddInterestStock(
          userId = user1.id,
          stockId = stockSamsung.id,
          interestGroupId = -1,
        )

        shouldThrow<InterestGroupNotFoundException> {
          interestStockService.addInterestStock(invalidPayload)
        }
      }
    }
  }

  describe("addInterestStock - 이미 추가된 주식을 추가하는 경우") {
    val user1 = userRepository.save(UserFixtures.user1())
    val stockSamsung = stockRepository.save(StockFixtures.stockSamsung())
    val interestGroup = interestGroupRepository.save(InterestGroup(name = "Group 1", user = user1))

    interestStockRepository.save(InterestStock(user = user1, stock = stockSamsung, group = interestGroup))

    context("사용자가 특정 그룹에 이미 추가한 주식을 추가하는 경우") {
      it("DuplicateStockInGroupException 발생한다.") {
        val payload = AddInterestStock(
          userId = user1.id,
          stockId = stockSamsung.id,
          interestGroupId = interestGroup.id,
        )

        shouldThrow<DuplicateStockInGroupException> { interestStockService.addInterestStock(payload) }

        val results = interestStockRepository.findAll()
        results.size shouldBe 1
      }
    }
  }

  describe("findInterestStocks") {
    val user1 = userRepository.save(UserFixtures.user1())
    val user2 = userRepository.save(UserFixtures.user1())

    val stockSamsung = stockRepository.save(StockFixtures.stockSamsung())
    val stockNaver = stockRepository.save(StockFixtures.stockNaver())
    val stockKakao = stockRepository.save(StockFixtures.stockKakao())

    val interestGroup = interestGroupRepository.save(InterestGroup(name = "Group 1", user = user1))

    interestStockRepository.saveAll(
      listOf(
        InterestStock(user = user1, stock = stockSamsung, group = interestGroup),
        InterestStock(user = user1, stock = stockNaver, group = interestGroup),
        InterestStock(user = user1, stock = stockKakao, group = interestGroup),
      )
    )


    context("관심 주식이 존재하는 경우") {
      it("관심 주식 목록을 반환한다.") {
        val results = interestStockService.findInterestStocks(
          userId = user1.id,
          groupId = interestGroup.id,
        )

        results.size shouldBe 3

        val samsung = results.find { it.stockId == stockSamsung.id }!!

        samsung.stockName shouldBe "삼성전자보통주"
        samsung.stockShortName shouldBe "삼성전자"
      }
    }

    context("관심 주식이 없는 경우") {
      it("빈 목록을 반환한다.") {
        val results = interestStockService.findInterestStocks(
          userId = user2.id,
          groupId = interestGroup.id,
        )

        results.size shouldBe 0
      }
    }
  }
})

