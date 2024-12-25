package org.mj.passiveincome.system.security.token

import java.util.Date

interface Token {

  fun get(): String

  fun getExpiration(): Date

  val tokenType: TokenType
}
