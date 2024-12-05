package org.mj.passiveincome.system.core.base


open class BaseResponse(
  val code: String,
  val message: String,
) {
  companion object {
    fun of(status: BaseStatus, message: String): BaseResponse {
      return BaseResponse(
        code = status.code,
        message = message,
      )
    }

    fun ok(message: String = getBundleMessage(code = "base.success", defaultMessage = BaseStatus.OK.message)): BaseResponse {
      return of(
        status = BaseStatus.OK,
        message = message,
      )
    }

    fun fail(message: String = getBundleMessage(code = "base.fail", defaultMessage = BaseStatus.FAIL.message)): BaseResponse {
      return of(
        status = BaseStatus.FAIL,
        message = message,
      )
    }
  }
}
