package org.mj.passiveincome.app.service.user.account

import org.mj.passiveincome.domain.account.UserAccount
import org.mj.passiveincome.domain.account.UserAccountRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
  private val userRepository: UserRepository,
  private val userAccountRepository: UserAccountRepository
) {

  @Transactional
  fun registerAccount(request: AccountRegisterRequest) {
    val user = userRepository.findByIdOrThrow(request.userId) { throw IllegalArgumentException("User not found") }

    val userAccount = UserAccount(
      user = user,
      accountNumber = request.accountNumber,
    )
    userAccountRepository.save(userAccount)
  }
}
