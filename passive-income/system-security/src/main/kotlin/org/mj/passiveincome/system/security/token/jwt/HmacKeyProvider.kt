package org.mj.passiveincome.system.security.token.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.mj.passiveincome.system.security.token.TokenKeyProvider
import javax.crypto.SecretKey

class HmacKeyProvider(
  private val plainTextKey: String
) : TokenKeyProvider {
  init {
    require(plainTextKey.isNotBlank()) { "plainTextKey must not be blank" }
  }

  override fun getKey(): SecretKey {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(plainTextKey))
  }
}
