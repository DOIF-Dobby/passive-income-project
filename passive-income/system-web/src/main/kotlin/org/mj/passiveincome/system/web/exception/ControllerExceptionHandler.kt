package org.mj.passiveincome.system.web.exception

import org.mj.passiveincome.system.core.base.BaseException
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.core.logging.error
import org.mj.passiveincome.system.core.logging.logger
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

  /**
   * ApiException 처리
   */
  @ExceptionHandler(ApiException::class)
  fun handleApiException(e: ApiException): ResponseEntity<BaseResponse> {
    logger.error { "${e::class.simpleName}: ${e.message} | localizedMessage: ${e.localizedMessage} | status: ${e.status} | httpStatus: ${e.httpStatus}" }

    return ResponseEntity.status(e.httpStatus)
      .body(BaseResponse.fail(e.localizedMessage))
  }

  /**
   * BaseException 처리
   */
  @ExceptionHandler(BaseException::class)
  fun handleBaseException(e: BaseException): ResponseEntity<BaseResponse> {
    logger.error { "${e::class.simpleName}: ${e.message} | localizedMessage: ${e.localizedMessage} | status: ${e.status}" }

    return ResponseEntity.badRequest()
      .body(BaseResponse.fail(e.localizedMessage))
  }

  /**
   * BindException 처리
   */
  @ExceptionHandler(BindException::class)
  fun handleBindException(e: BindException): ResponseEntity<BaseResponseDetail<Map<String, String>>> {
    logger.error { "${e::class.simpleName}: ${e.message}" }


    val invalidFieldMap = mutableMapOf<String, String>()
    val bindingResult = e.bindingResult

    bindingResult.fieldErrors.forEach { fieldError ->
      invalidFieldMap[fieldError.field] = fieldError.defaultMessage ?: ""
    }


    return ResponseEntity.badRequest()
      .body(
        BaseResponseDetail.fail(
          data = invalidFieldMap,
          message = "Invalid request"
        )
      )
  }
}
