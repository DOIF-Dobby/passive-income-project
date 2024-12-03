package org.mj.passiveincome.app.api.features.account.service

data class AccountRegisterRequest(
  val userId: Long,
  val accountNumber: String,
)
