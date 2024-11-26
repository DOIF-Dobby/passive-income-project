package org.mj.passiveincome.core.money

import org.mj.passiveincome.core.base.BaseException

class CurrencyMismatchException : BaseException(message = "Currency mismatch")

class NonFractionalCurrencyException : BaseException(message = "Non fractional currency")
