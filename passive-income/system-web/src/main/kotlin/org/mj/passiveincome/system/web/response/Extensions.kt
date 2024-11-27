package org.mj.passiveincome.system.web.response

import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.core.base.BaseStatus

typealias BaseResponseContent<T> = BaseResponseDetail<Content<T>>

fun <T> BaseResponseDetail.Companion.content(data: List<T>, message: String? = null): BaseResponseContent<T> {
  return this.from(
    status = BaseStatus.SUCCESS,
    message = message,
    data = Content.of(data)
  )
}
