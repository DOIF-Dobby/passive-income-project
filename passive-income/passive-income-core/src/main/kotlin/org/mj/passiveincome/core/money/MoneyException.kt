package org.mj.passiveincome.core.money

open class MoneyException(message: String) : RuntimeException(message)

class CurrencyMismatchException : MoneyException("Currency mismatch")

class NonFractionalCurrencyException : MoneyException("Non fractional currency")