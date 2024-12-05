package org.mj.passiveincome.system.core.base


class BaseResponseDetail<T>(
  code: String,
  message: String,
  val data: T
) : BaseResponse(code, message) {
  companion object {
    fun <T> of(status: BaseStatus, data: T, message: String): BaseResponseDetail<T> {
      return BaseResponseDetail(
        code = status.code,
        message = message,
        data = data
      )
    }

    fun <T> ok(data: T, message: String = getBundleMessage(code = "base.success", defaultMessage = BaseStatus.OK.message)): BaseResponseDetail<T> {
      return of(
        status = BaseStatus.OK,
        message = message,
        data = data
      )
    }

    fun <T> fail(data: T, message: String = getBundleMessage(code = "base.fail", defaultMessage = BaseStatus.FAIL.message)): BaseResponseDetail<T> {
      return of(
        status = BaseStatus.FAIL,
        message = message,
        data = data
      )
    }
  }
}
