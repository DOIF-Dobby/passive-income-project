package org.mj.passiveincome.system.core.base


open class BaseResponse(
  val code: String,
  val message: String,
) {
  
  companion object {
    fun from(status: BaseStatus, message: String? = null): BaseResponse {
      return BaseResponse(
        code = status.code,
        message = message ?: status.message,
      )
    }

    fun ok(message: String? = null): BaseResponse {
      return from(
        status = BaseStatus.SUCCESS,
        message = message,
      )
    }

    fun fail(message: String? = null): BaseResponse {
      return from(
        status = BaseStatus.FAIL,
        message = message,
      )
    }
  }
}
