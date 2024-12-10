package org.mj.passiveincome.app.api.features.user.service

import org.mj.passiveincome.domain.account.UserAccount
import org.mj.passiveincome.domain.account.UserAccountRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountService(
  private val userRepository: UserRepository,
  private val userAccountRepository: UserAccountRepository
) {

  /**
   * 계좌 등록
   */
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

