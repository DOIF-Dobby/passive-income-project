package org.mj.passiveincome.system.web.exception

import org.mj.passiveincome.system.core.base.BaseException
import org.mj.passiveincome.system.core.base.BaseStatus
import org.springframework.http.HttpStatus

abstract class ApiException(
  val httpStatus: HttpStatus,
  status: BaseStatus = BaseStatus.FAIL,
  message: String = status.message,
  messageProperty: String,
  messageArguments: Array<Any>? = null,
) : BaseException(
  status = status,
  message = message,
  messageProperty = messageProperty,
  messageArguments = messageArguments,
)

abstract class NotFoundApiException(
  message: String = "Not found",
  messageProperty: String,
  messageArguments: Array<Any>? = null,
) : ApiException(
  httpStatus = HttpStatus.NOT_FOUND,
  message = message,
  messageProperty = messageProperty,
  messageArguments = messageArguments,
)

class UnauthorizedApiException : ApiException(
  httpStatus = HttpStatus.UNAUTHORIZED,
  message = "Unauthorized",
  messageProperty = "web.error.unauthorized",
)
