package org.mj.passiveincome.system.core.base


class BaseResponseDetail<T>(
  code: String,
  message: String,
  val detail: T
) : BaseResponse(code, message) {
  companion object {
    fun <T> of(status: BaseStatus, detail: T, message: String): BaseResponseDetail<T> {
      return BaseResponseDetail(
        code = status.code,
        message = message,
        detail = detail
      )
    }

    fun <T> ok(detail: T, message: String = getBundleMessage(code = "base.ok", defaultMessage = BaseStatus.OK.message)): BaseResponseDetail<T> {
      return of(
        status = BaseStatus.OK,
        message = message,
        detail = detail
      )
    }

    fun <T> fail(detail: T, message: String = getBundleMessage(code = "base.fail", defaultMessage = BaseStatus.FAIL.message)): BaseResponseDetail<T> {
      return of(
        status = BaseStatus.FAIL,
        message = message,
        detail = detail
      )
    }
  }
}
