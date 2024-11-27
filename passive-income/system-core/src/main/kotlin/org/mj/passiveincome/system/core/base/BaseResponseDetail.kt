package org.mj.passiveincome.system.core.base


class BaseResponseDetail<T>(
  code: String,
  message: String,
  val data: T
) : BaseResponse(code, message) {
  companion object {
    fun <T> from(status: BaseStatus, data: T, message: String?): BaseResponseDetail<T> {
      return BaseResponseDetail(
        code = status.code,
        message = message ?: status.message,
        data = data
      )
    }

    fun <T> ok(data: T, message: String? = null): BaseResponseDetail<T> {
      return from(
        status = BaseStatus.SUCCESS,
        message = message,
        data = data
      )
    }

    fun <T> fail(data: T, message: String? = null): BaseResponseDetail<T> {
      return from(
        status = BaseStatus.FAIL,
        message = message,
        data = data
      )
    }
  }
}
