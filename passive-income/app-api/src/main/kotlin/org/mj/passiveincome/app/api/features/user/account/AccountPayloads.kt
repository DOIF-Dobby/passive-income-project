package org.mj.passiveincome.app.api.features.user.account

data class AccountRegisterRequest(
  val userId: Long,
  val accountNumber: String,
)
