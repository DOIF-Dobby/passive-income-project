package org.mj.passiveincome.app.service.user.credentials

import org.mj.passiveincome.core.extension.findByIdOrThrow
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
