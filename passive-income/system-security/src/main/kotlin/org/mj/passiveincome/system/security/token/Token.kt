package org.mj.passiveincome.system.security.token

import java.util.Date

interface Token {

  fun value(): String

  fun getExpiration(): Date

  val tokenType: TokenType
}
