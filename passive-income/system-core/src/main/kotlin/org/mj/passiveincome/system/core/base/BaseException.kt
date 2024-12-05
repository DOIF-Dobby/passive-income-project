package org.mj.passiveincome.system.core.base

abstract class BaseException(
  val status: BaseStatus = BaseStatus.FAIL,
  override val message: String = status.message, // 내부용 메시지
  val messageProperty: String = "base.fail", // 외부용 메시지
  val messageArguments: Array<Any>? = null,
) : RuntimeException(message) {

  fun getLocalizedMessage(): String {
    return getBundleMessage(messageProperty, messageArguments, message)
  }
}
