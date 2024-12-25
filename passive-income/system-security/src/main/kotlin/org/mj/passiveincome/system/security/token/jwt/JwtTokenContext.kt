package org.mj.passiveincome.system.security.token.jwt

import org.mj.passiveincome.system.security.token.TokenContext
import java.time.Duration

class JwtTokenContext(
  private val subject: String,
  private val expireDuration: Duration,
  private val payload: Map<String, Any> = emptyMap()
) : TokenContext {
  override fun getSubject(): String {
    return subject
  }

  override fun getExpireDuration(): Duration {
    return expireDuration
  }

  override fun getPayload(): Map<String, Any> {
    return payload
  }
}
