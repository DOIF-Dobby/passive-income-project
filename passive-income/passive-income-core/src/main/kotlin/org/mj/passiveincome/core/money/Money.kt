package org.mj.passiveincome.core.money

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

@Embeddable
class Money(
	@Enumerated(EnumType.STRING) val currency: Currency,
	val amount: BigDecimal,
) : Comparable<Money> {

	init {
		// 소수점이 붙으면 안되는 통화 타입의 경우, 소수점이 있으면 해당 값이 0인지 확인
		// 0이 아니면 Money 객체 생성 불가
		if (currency.scale == 0 && amount.scale() > 0) {
			val decimalPart = amount.remainder(BigDecimal.ONE)
			require(decimalPart.compareTo(BigDecimal.ZERO) == 0) { throw NonFractionalCurrencyException() }
		}
	}

	val isKrw get() = currency == Currency.KRW
	val isUsd get() = currency == Currency.USD

	fun isSameCurrency(other: Money) = currency == other.currency

	fun exchangeKrw(exchangeRate: Double): Money {
		require(currency == Currency.USD) { throw IllegalArgumentException("Only USD can be converted to KRW") }
		val changedAmount = amount * BigDecimal(exchangeRate)
		return krw(changedAmount.setScale(Currency.KRW.scale, Currency.KRW.roundingMode))
	}

	fun formatted(): String {
		val scaledAmount = amount.setScale(currency.scale, currency.roundingMode)
		return "$currency ${currency.decimalFormat.format(scaledAmount)}"
	}

	// 공통 검사 메서드
	private fun validateSameCurrency(other: Money) {
		require(currency == other.currency) { throw CurrencyMismatchException() }
	}

	private fun operateMoney(other: Money, operator: (BigDecimal, BigDecimal) -> BigDecimal): Money {
		validateSameCurrency(other)
		return Money(currency, operator(amount, other.amount))
	}

	operator fun plus(other: Money) = operateMoney(other) { a, b -> a + b }
	operator fun minus(other: Money) = operateMoney(other) { a, b -> a - b }
	operator fun times(other: Number) = Money(currency, amount * BigDecimal(other.toString()))
	operator fun div(other: Number) = Money(currency, amount.divide(BigDecimal(other.toString()), currency.scale, currency.roundingMode))

	operator fun compareTo(other: Number) = amount.compareTo(BigDecimal(other.toString()))

	override fun compareTo(other: Money): Int {
		validateSameCurrency(other)
		return amount.compareTo(other.amount)
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Money

		return currency == other.currency && amount.compareTo(other.amount) == 0
	}

	override fun toString(): String {
		return formatted()
	}

	override fun hashCode(): Int {
		var result = currency.hashCode()
		result = 31 * result + amount.hashCode()
		return result
	}

	companion object {
		fun of(currency: Currency, amount: BigDecimal) = Money(currency, amount)

		fun krw(amount: BigDecimal) = Money(Currency.KRW, amount)
		fun krw(amount: Number) = Money(Currency.KRW, BigDecimal(amount.toString()))

		fun usd(amount: BigDecimal) = Money(Currency.USD, amount)
		fun usd(amount: Number) = Money(Currency.USD, BigDecimal(amount.toString()))
	}
}
