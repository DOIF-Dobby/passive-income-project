package org.mj.passiveincome.system.web.exception

import org.mj.passiveincome.system.core.base.BaseException
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.logging.error
import org.mj.passiveincome.system.core.logging.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

  @ExceptionHandler(ApiException::class)
  fun handleApiException(e: ApiException): ResponseEntity<BaseResponse> {
    logger.error { "${e::class.simpleName}: ${e.message}. status:${e.status} httpStatus: ${e.httpStatus}" }

    return ResponseEntity.status(e.httpStatus)
      .body(BaseResponse.fail(e.message))
  }

  @ExceptionHandler(BaseException::class)
  fun handleBaseException(e: BaseException): ResponseEntity<BaseResponse> {
    logger.error { "${e::class.simpleName}: ${e.message}. status: ${e.status}" }

    return ResponseEntity.badRequest()
      .body(BaseResponse.fail(e.message))
  }
}
