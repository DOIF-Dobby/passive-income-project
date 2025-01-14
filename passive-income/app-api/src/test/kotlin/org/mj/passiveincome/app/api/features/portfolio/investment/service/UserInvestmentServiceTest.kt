package org.mj.passiveincome.app.api.features.portfolio.investment.service

import AppApiDataJpaTest
import DummyAuthUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.domain.portfolio.investment.UserInvestmentRepository
import org.mj.passiveincome.domain.portfolio.investment.service.AddUserInvestmentService
import org.mj.passiveincome.domain.trading.TradingStrategy
import org.mj.passiveincome.domain.trading.TradingStrategyAccessType
import org.mj.passiveincome.domain.trading.TradingStrategyRepository
import org.mj.passiveincome.domain.trading.TradingStrategyVisibility
import org.mj.passiveincome.domain.trading.service.CannotAccessTradingStrategyException
import org.mj.passiveincome.domain.trading.service.TradingStrategyAccessChecker
import org.mj.passiveincome.domain.user.GroupFixtures
import org.mj.passiveincome.domain.user.GroupRepository
import org.mj.passiveincome.domain.user.GroupUser
import org.mj.passiveincome.domain.user.GroupUserRepository
import org.mj.passiveincome.domain.user.GroupUserRole
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository

@AppApiDataJpaTest
class UserInvestmentServiceTest(
  val userRepository: UserRepository,
  val tradingStrategyRepository: TradingStrategyRepository,
  val userInvestmentRepository: UserInvestmentRepository,
  val groupUserRepository: GroupUserRepository,
  val groupRepository: GroupRepository,
) : DescribeSpec({

  val tradingStrategyAccessChecker = TradingStrategyAccessChecker(
    groupUserRepository = groupUserRepository,
  )

  val addUserInvestmentService = AddUserInvestmentService(
    userInvestmentRepository = userInvestmentRepository,
    tradingStrategyAccessChecker = tradingStrategyAccessChecker
  )

  val userInvestmentService = UserInvestmentService(
    userRepository = userRepository,
    tradingStrategyRepository = tradingStrategyRepository,
    addUserInvestmentService = addUserInvestmentService,
  )

  describe("addUserInvestment - 사용자 투자 추가 정상 케이스 1") {
    context("거래 전략이 공개 상태인 경우") {
      it("정상적으로 등록된다.") {
        val user = DummyAuthUtil.dobby(userRepository)
        val tradingStrategy = TradingStrategy(
          name = "전략",
          owner = userRepository.save(UserFixtures.myungJin()),
          visibility = TradingStrategyVisibility.PUBLIC
        )

        tradingStrategyRepository.save(tradingStrategy)

        userInvestmentService.addUserInvestment(
          AddUserInvestment(
            tradingStrategyId = tradingStrategy.id
          )
        )

        val userInvestment = userInvestmentRepository.findAll().first()

        userInvestment.user.id shouldBe user.id
        userInvestment.tradingStrategy.id shouldBe tradingStrategy.id
      }
    }
  }

  describe("addUserInvestment - 사용자 투자 추가 정상 케이스 2") {
    context("사용자가 전략의 소유자인 경우") {
      it("정상적으로 등록된다.") {
        val user = DummyAuthUtil.dobby(userRepository)
        val tradingStrategy = TradingStrategy(
          name = "전략",
          owner = user,
          visibility = TradingStrategyVisibility.PRIVATE
        )

        tradingStrategyRepository.save(tradingStrategy)

        userInvestmentService.addUserInvestment(
          AddUserInvestment(
            tradingStrategyId = tradingStrategy.id
          )
        )

        val userInvestment = userInvestmentRepository.findAll().first()

        userInvestment.user.id shouldBe user.id
        userInvestment.tradingStrategy.id shouldBe tradingStrategy.id
      }
    }
  }

  describe("addUserInvestment - 사용자 투자 추가 정상 케이스 3") {
    context("소유자는 아니지만 해당 사용자가 접근 가능 주체 그룹에 속한 경우") {
      it("정상적으로 등록된다.") {
        val user = DummyAuthUtil.dobby(userRepository)
        val group = groupRepository.save(GroupFixtures.group1())
        groupUserRepository.save(
          GroupUser(
            user = user,
            group = group,
            role = GroupUserRole.MEMBER,
          )
        )

        val tradingStrategy = TradingStrategy(
          name = "전략",
          owner = userRepository.save(UserFixtures.myungJin()),
          visibility = TradingStrategyVisibility.PRIVATE
        )

        tradingStrategy.addAccessibleSubject(
          subjectId = group.id,
          accessType = TradingStrategyAccessType.GROUP
        )

        tradingStrategyRepository.save(tradingStrategy)

        userInvestmentService.addUserInvestment(
          AddUserInvestment(
            tradingStrategyId = tradingStrategy.id
          )
        )

        val userInvestment = userInvestmentRepository.findAll().first()

        userInvestment.user.id shouldBe user.id
        userInvestment.tradingStrategy.id shouldBe tradingStrategy.id
      }
    }
  }

  describe("addUserInvestment - 사용자 투자 추가 정상 케이스 4") {
    context("소유자는 아니지만 해당 사용자가 접근 가능 주체에 직접 속하는 경우") {
      it("정상적으로 등록된다.") {
        val user = DummyAuthUtil.dobby(userRepository)

        val tradingStrategy = TradingStrategy(
          name = "전략",
          owner = userRepository.save(UserFixtures.myungJin()),
          visibility = TradingStrategyVisibility.PRIVATE
        )

        tradingStrategy.addAccessibleSubject(
          subjectId = user.id,
          accessType = TradingStrategyAccessType.USER
        )

        tradingStrategyRepository.save(tradingStrategy)

        userInvestmentService.addUserInvestment(
          AddUserInvestment(
            tradingStrategyId = tradingStrategy.id
          )
        )

        val userInvestment = userInvestmentRepository.findAll().first()

        userInvestment.user.id shouldBe user.id
        userInvestment.tradingStrategy.id shouldBe tradingStrategy.id
      }
    }
  }

  describe("addUserInvestment - 사용자 투자 추가 예외 케이스") {
    context("소유자가 아니고 전략이 비공개인 경우") {
      it("CannotAccessTradingStrategyException이 발생한다.") {
        DummyAuthUtil.dobby(userRepository)

        val tradingStrategy = TradingStrategy(
          name = "전략",
          owner = userRepository.save(UserFixtures.myungJin()),
          visibility = TradingStrategyVisibility.PRIVATE
        )

        tradingStrategyRepository.save(tradingStrategy)

        shouldThrow<CannotAccessTradingStrategyException> {
          userInvestmentService.addUserInvestment(
            AddUserInvestment(
              tradingStrategyId = tradingStrategy.id
            )
          )
        }
      }
    }
  }
})
