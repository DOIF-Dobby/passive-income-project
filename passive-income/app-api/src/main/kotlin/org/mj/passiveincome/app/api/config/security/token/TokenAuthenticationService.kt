package org.mj.passiveincome.app.api.config.security.token

import org.springframework.security.core.Authentication

interface TokenAuthenticationService {

  fun authenticate(token: String): Authentication
}
