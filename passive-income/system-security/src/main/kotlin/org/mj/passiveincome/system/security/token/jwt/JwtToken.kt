package org.mj.passiveincome.system.security.token.jwt

import org.mj.passiveincome.system.security.token.Token
import org.mj.passiveincome.system.security.token.TokenType

class JwtToken(
  private val token: String,
) : Token {
  override fun value(): String {
    return token
  }

  override val tokenType = TokenType.JWT
}
