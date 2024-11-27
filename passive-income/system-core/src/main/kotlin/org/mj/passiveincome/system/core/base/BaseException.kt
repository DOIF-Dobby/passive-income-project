package org.mj.passiveincome.system.core.base

import java.io.Serial

open class BaseException(
  val status: BaseStatus = BaseStatus.FAIL,
  message: String = status.message,
) : RuntimeException(message) {

  companion object {
    @Serial
    private const val serialVersionUID: Long = -3229256567936011611L
  }
}
