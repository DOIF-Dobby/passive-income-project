package org.mj.passiveincome.domain.finance

import org.mj.passiveincome.system.core.base.BaseException
import java.io.Serial

class CurrencyMismatchException : BaseException(message = "Currency mismatch") {
  companion object {
    @Serial
    private const val serialVersionUID: Long = 849264000909147227L
  }
}

class NonFractionalCurrencyException : BaseException(message = "Non fractional currency") {
  companion object {
    @Serial
    private const val serialVersionUID: Long = -6248498742031423607L
  }
}
