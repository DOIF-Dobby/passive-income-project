package org.mj.passiveincome.app.api.features.credentials.service

data class RegisterKisCredentials(
  val userId: Long,
  val appKey: String,
  val appSecretKey: String,
)
