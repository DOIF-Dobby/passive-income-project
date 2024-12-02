package org.mj.passiveincome.app.api.features.credentials.service

import org.mj.passiveincome.domain.credentials.UserKisCredentials
import org.mj.passiveincome.domain.credentials.UserKisCredentialsRepository
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.system.data.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KisCredentialsService(
  private val userRepository: UserRepository,
  private val userKisCredentialsRepository: UserKisCredentialsRepository,
) {

  @Transactional
  fun registerKisCredentials(request: KisCredentialsRegisterRequest) {
    val user = userRepository.findByIdOrThrow(request.userId)

    val userKisCredentials = UserKisCredentials(
      user = user,
      appKey = request.appKey,
      appSecretKey = request.appSecretKey,
    )
    userKisCredentialsRepository.save(userKisCredentials)
  }
}
