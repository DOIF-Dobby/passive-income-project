package org.mj.passiveincome.app.api.features.account.service

import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.account.UserAccount
import org.mj.passiveincome.domain.account.UserAccountRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
  private val userRepository: UserRepository,
  private val userAccountRepository: UserAccountRepository
) {

  @Transactional
  fun registerAccount(payload: RegisterAccount) {
    val user = UserServiceHelper.findUser(userRepository, payload.userId)

    val userAccount = UserAccount(
      user = user,
      accountNumber = payload.accountNumber,
    )
    userAccountRepository.save(userAccount)
  }
}

