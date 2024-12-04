package org.mj.passiveincome.system.core.base


open class BaseResponse(
  val code: String,
  val message: String,
) {

  companion object {
    fun of(status: BaseStatus, message: String = status.message): BaseResponse {
      return BaseResponse(
        code = status.code,
        message = message,
      )
    }

    fun ok(message: String = BaseStatus.SUCCESS.message): BaseResponse {
      return of(
        status = BaseStatus.SUCCESS,
        message = message,
      )
    }

    fun fail(message: String = BaseStatus.FAIL.message): BaseResponse {
      return of(
        status = BaseStatus.FAIL,
        message = message,
      )
    }
  }
}
