package org.mj.passiveincome.domain.common.email

import org.mj.passiveincome.system.core.base.BaseException

class InvalidEmailException : BaseException(message = "Invalid email") {
}
