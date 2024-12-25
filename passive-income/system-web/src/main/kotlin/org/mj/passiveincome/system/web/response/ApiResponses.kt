package org.mj.passiveincome.system.web.response

import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.BaseStatus
import org.mj.passiveincome.system.core.base.messageAccessor

/**
 * 인증 실패 응답
 */
class UnauthorizedResponse : BaseResponse(
  code = BaseStatus.FAIL.code,
  message = messageAccessor.getMessage("web.error.unauthorized"),
)
