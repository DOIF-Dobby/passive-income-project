package org.mj.passiveincome.system.web.exception

import org.mj.passiveincome.system.core.base.BaseException
import org.mj.passiveincome.system.core.base.BaseStatus
import org.springframework.http.HttpStatus

abstract class ApiException(
  val httpStatus: HttpStatus,
  status: BaseStatus = BaseStatus.FAIL,
  message: String? = status.message,
) : BaseException(status, message)
