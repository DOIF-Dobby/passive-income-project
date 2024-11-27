package org.mj.passiveincome.domain.common.email

import org.mj.passiveincome.system.core.base.BaseException
import java.io.Serial

class InvalidEmailException : BaseException(message = "Invalid email") {
  companion object {
    @Serial
    private const val serialVersionUID: Long = -5198603910373850499L
  }
}
