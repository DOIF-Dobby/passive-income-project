package org.mj.passiveincome.domain.credentials.service

import org.mj.passiveincome.domain.credentials.UserKisCredentials
import org.mj.passiveincome.domain.credentials.UserKisCredentialsRepository
import org.springframework.stereotype.Service

@Service
class UserKisCredentialsDomainService(
  private val userKisCredentialsRepository: UserKisCredentialsRepository,
) {

  /**
   * KIS 인증 정보 등록
   */
  fun registerKisCredentials(entity: UserKisCredentials): UserKisCredentials {
    // 사용자가 이미 등록한 KIS 인증 정보가 있으면 예외를 발생시킨다.
    // 사용자당 KIS 인증 정보는 하나만 등록할 수 있다.
    if (userKisCredentialsRepository.existsByUser(entity.user)) {
      throw UserKisCredentialsAlreadyRegisteredException()
    }

    return userKisCredentialsRepository.save(entity)
  }
}
