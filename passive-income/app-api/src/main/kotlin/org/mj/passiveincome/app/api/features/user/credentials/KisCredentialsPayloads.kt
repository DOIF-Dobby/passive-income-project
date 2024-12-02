package org.mj.passiveincome.app.api.features.user.credentials

data class KisCredentialsRegisterRequest(
  val userId: Long,
  val appKey: String,
  val appSecretKey: String,
)
