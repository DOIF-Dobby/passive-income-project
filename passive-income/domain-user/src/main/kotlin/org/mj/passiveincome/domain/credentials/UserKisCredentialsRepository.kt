package org.mj.passiveincome.domain.credentials

import org.springframework.data.jpa.repository.JpaRepository

interface UserKisCredentialsRepository : JpaRepository<UserKisCredentials, Long> {
}
