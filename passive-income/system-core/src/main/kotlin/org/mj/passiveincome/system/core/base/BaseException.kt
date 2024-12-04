package org.mj.passiveincome.system.core.base

abstract class BaseException(
  val status: BaseStatus = BaseStatus.FAIL,
  override val message: String = status.message,
) : RuntimeException(message)
