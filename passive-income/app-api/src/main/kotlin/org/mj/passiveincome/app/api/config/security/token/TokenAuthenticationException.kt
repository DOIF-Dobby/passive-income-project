package org.mj.passiveincome.app.api.config.security.token

import org.springframework.security.core.AuthenticationException

class TokenAuthenticationException : AuthenticationException("token is invalid")
