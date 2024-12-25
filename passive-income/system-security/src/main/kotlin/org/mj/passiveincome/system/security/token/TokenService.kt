package org.mj.passiveincome.system.security.token

interface TokenService {

  fun generateToken(context: TokenContext): Token

  fun validateToken(token: String): Boolean

  fun getPayloadValue(token: String, payloadKey: String): Any?

}
