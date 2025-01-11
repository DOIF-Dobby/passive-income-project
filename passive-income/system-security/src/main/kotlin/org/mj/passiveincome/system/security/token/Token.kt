package org.mj.passiveincome.system.security.token

interface Token {

  fun value(): String

  fun bearer(): String {
    return "Bearer ${value()}"
  }

  val tokenType: TokenType
}
