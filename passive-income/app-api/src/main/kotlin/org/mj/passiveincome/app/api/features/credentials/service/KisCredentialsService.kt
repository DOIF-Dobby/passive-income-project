package org.mj.passiveincome.app.api.features.credentials.service

import org.mj.passiveincome.app.api.features.user.service.UserServiceHelper
import org.mj.passiveincome.domain.credentials.UserKisCredentials
import org.mj.passiveincome.domain.credentials.UserKisCredentialsRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KisCredentialsService(
  private val userRepository: UserRepository,
  private val userKisCredentialsRepository: UserKisCredentialsRepository,
) {

  @Transactional
  fun registerKisCredentials(payload: RegisterKisCredentials) {
    val user = UserServiceHelper.findUser(userRepository, payload.userId)

    val userKisCredentials = UserKisCredentials(
      user = user,
      appKey = payload.appKey,
      appSecretKey = payload.appSecretKey,
    )
    userKisCredentialsRepository.save(userKisCredentials)
  }
}
