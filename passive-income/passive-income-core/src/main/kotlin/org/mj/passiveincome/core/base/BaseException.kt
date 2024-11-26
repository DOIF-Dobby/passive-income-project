package org.mj.passiveincome.core.base

open class BaseException(
  val status: BaseStatus = BaseStatus.FAIL,
  message: String = status.message,
) : RuntimeException(message) {
}
