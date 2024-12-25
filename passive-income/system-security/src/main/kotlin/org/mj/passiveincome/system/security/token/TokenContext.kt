package org.mj.passiveincome.system.security.token

import java.time.Duration

interface TokenContext {

  fun getSubject(): String

  fun getExpireDuration(): Duration

  fun getPayload(): Map<String, Any> = emptyMap()

}
