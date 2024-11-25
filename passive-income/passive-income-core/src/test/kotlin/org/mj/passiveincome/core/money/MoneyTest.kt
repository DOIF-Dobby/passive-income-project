package org.mj.passiveincome.core.money

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mj.passiveincome.core.money.exception.CurrencyMismatchException
import org.mj.passiveincome.core.money.exception.NonFractionalCurrencyException
import java.math.BigDecimal

class MoneyTest {

  @Test
  @DisplayName("Money 생성 및 동등성 테스트")
  fun moneyCreateAndEqualityTest() {
    // given
    val krw1 = Money(Currency.KRW, BigDecimal(10_000))
    val krw2 = Money(Currency.KRW, BigDecimal(10_000))
    val krw3 = Money.of(Currency.KRW, BigDecimal(10_000))
    val krw4 = Money.krw(BigDecimal(10_000))
    val krw5 = Money.krw(BigDecimal(10_000))
    val usd1 = Money.usd(BigDecimal(10_000))
    val usd2 = Money.usd(10_000)

    // when & then
    assertThat(krw1).isEqualTo(krw2)
    assertThat(krw1).isEqualTo(krw3)
    assertThat(krw1).isEqualTo(krw4)
    assertThat(krw1).isEqualTo(krw5)
    assertThat(krw1).isNotEqualTo(usd1)
    assertThat(usd1).isEqualTo(usd2)
    assertThrows<NonFractionalCurrencyException> { Money.krw(10_000.23) }.let { assertThat(it.message).isEqualTo("Non fractional currency") }
  }

  @Test
  @DisplayName("Money 사칙연산 테스트")
  fun moneyFourBasicOperationsTest() {
    // given
    val krw1 = Money.krw(10_000)
    val usd1 = Money.usd(10_000)

    // when
    val krwResult1 = krw1 + Money.krw(2_000)
    val krwResult2 = krw1 - Money.krw(2_000)
    val krwResult3 = krw1 * 2
    val krwResult4 = krw1 / 2

    val usdResult1 = usd1 + Money.usd(100.55)
    val usdResult2 = usd1 - Money.usd(100.55)
    val usdResult3 = usd1 * 2
    val usdResult4 = usd1 / 3


    // then
    assertThat(krwResult1).isEqualTo(Money.krw(12_000))
    assertThat(krwResult2).isEqualTo(Money.krw(8_000))
    assertThat(krwResult3).isEqualTo(Money.krw(20_000))
    assertThat(krwResult4).isEqualTo(Money.krw(5_000))

    assertThat(usdResult1).isEqualTo(Money.usd(10_100.55))
    assertThat(usdResult2).isEqualTo(Money.usd(9_899.45))
    assertThat(usdResult3).isEqualTo(Money.usd(20_000))
    assertThat(usdResult4).isEqualTo(Money.usd(3_333.33))
  }

  @Test
  @DisplayName("Money 비교 테스트")
  fun compareMoneyTest() {
    // given
    val krw1 = Money.krw(10_000)
    val krw2 = Money.krw(5_000)
    val krw3 = Money.krw(5_000)

    // when & then
    assertThat(krw1 > krw2).isTrue()
    assertThat(krw1 >= krw2).isTrue()
    assertThat(krw2 >= krw3).isTrue()
    assertThat(krw2 > krw3).isFalse()
    assertThat(krw2 < krw1).isTrue()
    assertThat(krw2 <= krw1).isTrue()
    assertThat(krw3 == krw2).isTrue()
  }

  @Test
  @DisplayName("다른 통화끼리는 덧셈 / 뺄셈 / 비교 불가능")
  fun impossibleOperationOtherCurrencyMoney() {
    fun assertThrowsCurrencyMismatchException(block: () -> Unit) {
      assertThrows<CurrencyMismatchException> { block() }.let { assertThat(it.message).isEqualTo("Currency mismatch") }
    }

    // given
    val krw = Money.krw(10_000)
    val usd = Money.usd(1_000)

    // when & then
    assertThrowsCurrencyMismatchException { krw + usd }
    assertThrowsCurrencyMismatchException { krw - usd }
    assertThrowsCurrencyMismatchException { krw > usd }
    assertThrowsCurrencyMismatchException { krw >= usd }
    assertThrowsCurrencyMismatchException { krw < usd }
    assertThrowsCurrencyMismatchException { krw <= usd }
  }

  @Test
  @DisplayName("Money 포맷팅 테스트")
  fun moneyFormatTest() {
    // given
    val krw = Money.krw(10_000)
    val usd = Money.usd(10.266)

    // when & then
    assertThat(krw.formatted()).isEqualTo("KRW 10,000")
    assertThat(usd.formatted()).isEqualTo("USD 10.27")

  }

  @Test
  @DisplayName("Money 통화 타입 관련 테스트")
  fun moneyCurrencyTest() {
    // given
    val krw = Money.krw(10_000)
    val usd = Money.usd(10.55)

    // when & then
    assertThat(krw.isKrw).isTrue()
    assertThat(krw.isUsd).isFalse()
    assertThat(usd.isKrw).isFalse()
    assertThat(usd.isUsd).isTrue()
    assertThat(krw.isSameCurrency(Money.krw(100))).isTrue()
    assertThat(krw.isSameCurrency(usd)).isFalse()

  }
}