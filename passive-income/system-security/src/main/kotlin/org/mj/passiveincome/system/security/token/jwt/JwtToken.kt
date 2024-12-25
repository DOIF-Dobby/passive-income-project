package org.mj.passiveincome.system.security.token.jwt

import org.mj.passiveincome.system.security.token.Token
import org.mj.passiveincome.system.security.token.TokenType
import java.util.Date

class JwtToken(
  private val token: String,
  private val expiration: Date
) : Token {
  override fun get(): String {
    return token
  }

  override fun getExpiration(): Date {
    return expiration
  }

  override val tokenType = TokenType.JWT
}
