package org.mj.passiveincome.app.service.user.credentials

data class KisCredentialsRegisterRequest(
  val userId: Long,
  val appKey: String,
  val appSecretKey: String,
)
