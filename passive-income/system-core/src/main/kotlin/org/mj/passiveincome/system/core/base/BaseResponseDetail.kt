package org.mj.passiveincome.system.core.base


class BaseResponseDetail<T>(
  code: String,
  message: String,
  val data: T
) : BaseResponse(code, message) {
  companion object {
    fun <T> of(status: BaseStatus, data: T, message: String = status.message): BaseResponseDetail<T> {
      return BaseResponseDetail(
        code = status.code,
        message = message ?: status.message,
        data = data
      )
    }

    fun <T> ok(data: T, message: String = BaseStatus.SUCCESS.message): BaseResponseDetail<T> {
      return of(
        status = BaseStatus.SUCCESS,
        message = message,
        data = data
      )
    }

    fun <T> fail(data: T, message: String = BaseStatus.FAIL.message): BaseResponseDetail<T> {
      return of(
        status = BaseStatus.FAIL,
        message = message,
        data = data
      )
    }
  }
}
