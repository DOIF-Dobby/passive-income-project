package org.mj.passiveincome.system.core.base


class BaseResponse<T>(
  val code: String,
  val message: String,
  val data: T? = null
) {
  companion object {
    fun <T> from(status: BaseStatus, message: String? = null, data: T? = null): BaseResponse<T> {
      return BaseResponse(
        code = status.code,
        message = message ?: status.message,
        data = data
      )
    }

    fun <T> ok(message: String? = null, data: T? = null): BaseResponse<T> {
      return from(
        status = BaseStatus.SUCCESS,
        message = message,
        data = data
      )
    }

    fun <T> fail(message: String? = null, data: T? = null): BaseResponse<T> {
      return from(
        status = BaseStatus.FAIL,
        message = message,
        data = data
      )
    }
  }
}
