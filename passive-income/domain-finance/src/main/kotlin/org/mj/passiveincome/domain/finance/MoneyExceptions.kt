package org.mj.passiveincome.domain.finance

import org.mj.passiveincome.system.core.base.BaseException

class CurrencyMismatchException : BaseException(message = "Currency mismatch")
class NonFractionalCurrencyException : BaseException(message = "Non fractional currency")
