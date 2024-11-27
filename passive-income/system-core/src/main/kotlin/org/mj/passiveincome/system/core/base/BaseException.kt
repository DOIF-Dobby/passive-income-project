package org.mj.passiveincome.system.core.base

open class BaseException(
  val status: BaseStatus = BaseStatus.FAIL,
  message: String = status.message,
) : RuntimeException(message) {
}
