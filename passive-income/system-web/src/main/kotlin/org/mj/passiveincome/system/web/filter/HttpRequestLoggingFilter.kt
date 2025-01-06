package org.mj.passiveincome.system.web.filter

import jakarta.servlet.http.HttpServletRequest
import org.mj.passiveincome.system.core.logging.log
import org.springframework.web.filter.AbstractRequestLoggingFilter

class HttpRequestLoggingFilter : AbstractRequestLoggingFilter() {
  override fun beforeRequest(request: HttpServletRequest, message: String) {
    log.info("Request: [ ${request.method} ${request.requestURI} ] Remote host: [ ${request.remoteHost} ]")
  }

  override fun afterRequest(request: HttpServletRequest, message: String) {

  }
}
