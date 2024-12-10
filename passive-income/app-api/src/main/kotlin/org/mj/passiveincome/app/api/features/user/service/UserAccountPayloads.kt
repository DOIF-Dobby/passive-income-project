package org.mj.passiveincome.app.api.features.user.service

/**
 * 계좌 등록
 */
data class RegisterAccount(
  val userId: Long,
  val accountNumber: String,
)
