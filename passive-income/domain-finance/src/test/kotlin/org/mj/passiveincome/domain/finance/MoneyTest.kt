package org.mj.passiveincome.domain.finance

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.math.BigDecimal

class MoneyTest : DescribeSpec({

  describe("Money 객체 생성 시") {
    context("금액이 통화 타입에 맞는 경우") {
      val krw = Money.krw(10_000)
      val usd = Money.usd(100.55)

      it("주어진 금액을 갖는 Money 객체를 생성한다") {
        krw.currency shouldBe Currency.KRW
        krw.amount shouldBe BigDecimal("10000")
        usd.currency shouldBe Currency.USD
        usd.amount shouldBe BigDecimal("100.55")
      }
    }

    context("통화 타입에 설정된 소수점 자리수를 넘어가는 경우") {
      val krw1 = Money.krw(100.123456789)
      val krw2 = Money.krw(125.19987)
      val krw3 = Money.krw(55.74678)

      val usd1 = Money.usd(100.123456789)
      val usd2 = Money.usd(100.19987)
      val usd3 = Money.usd(100.14678)

      it("통화 타입에 설정된 자리수만큼 소수점을 적용한 금액을 갖는 Money 객체를 생성한다") {
        krw1.amount shouldBe BigDecimal("100")
        krw2.amount shouldBe BigDecimal("125")
        krw3.amount shouldBe BigDecimal("55")

        usd1.amount shouldBe BigDecimal("100.12")
        usd2.amount shouldBe BigDecimal("100.20")
        usd3.amount shouldBe BigDecimal("100.15")
      }
    }

  }


  describe("Money 객체 비교 시") {
    context("같은 통화 타입과 같은 금액을 가진 경우") {
      val krw1 = Money(Currency.KRW, BigDecimal(10_000))
      val krw2 = Money(Currency.KRW, BigDecimal(10_000))
      val krw3 = Money.of(Currency.KRW, BigDecimal(10_000))
      val krw4 = Money.krw(BigDecimal(10_000))
      val usd1 = Money.usd(BigDecimal(10_000))
      val usd2 = Money.usd(10_000)

      it("두 Money 객체는 동등하다.") {
        krw1 shouldBe krw2
        krw1 shouldBe krw3
        krw1 shouldBe krw4
        usd1 shouldBe usd2
      }
    }

    context("두 Money 객체가 다른 통화 타입을 가진 경우") {
      val krw = Money.krw(10_000)
      val usd = Money.usd(10_000)

      it("두 Money 객체는 동등하지 않다.") {
        krw shouldNotBe usd
      }
    }

    context("두 Money 객체가 다른 금액을 가진 경우") {
      val krw1 = Money.krw(10_000)
      val krw2 = Money.krw(2_000)

      it("두 Money 객체는 동등하지 않다.") {
        krw1 shouldNotBe krw2
      }
    }
  }

  describe("두 Money 객체 덧셈 시") {
    context("같은 통화 타입의 경우") {
      val krw1 = Money.krw(10_000)
      val krw2 = Money.krw(2_000)

      it("두 Money 객체의 금액을 더한 Money 객체를 반환한다.") {
        val krwResult = krw1 + krw2
        krwResult shouldBe Money.krw(12_000)
      }
    }

    context("통화 타입이 다를 경우") {
      val krw = Money.krw(10_000)
      val usd = Money.usd(10_000)

      it("CurrencyMismatchException이 발생한다.") {
        val exception = shouldThrow<CurrencyMismatchException> { krw + usd }
        exception.message shouldBe "Currency mismatch"
      }
    }
  }

  describe("두 Money 객체 뺄셈 시") {
    context("같은 통화 타입의 경우") {
      val krw1 = Money.krw(10_000)
      val krw2 = Money.krw(2_000)

      it("두 Money 객체의 금액을 뺀 Money 객체를 반환한다.") {
        val krwResult = krw1 - krw2
        krwResult shouldBe Money.krw(8_000)
      }
    }

    context("통화 타입이 다를 경우") {
      val krw = Money.krw(10_000)
      val usd = Money.usd(10_000)

      it("CurrencyMismatchException이 발생한다.") {
        val exception = shouldThrow<CurrencyMismatchException> { krw - usd }
        exception.message shouldBe "Currency mismatch"
      }
    }
  }

  describe("Money 객체 곱셈 시") {
    context("0이 아닌 수를 곱하는 경우") {
      it("Money 객체의 금액을 곱한 Money 객체를 반환한다.") {
        Money.krw(10_000) * 2 shouldBe Money.krw(20_000)
        Money.usd(51.58) * 2 shouldBe Money.usd(103.16)
      }
    }

    context("0을 곱하는 경우") {
      it("0을 가진 Money 객체를 반환한다.") {
        Money.krw(10_000) * 0 shouldBe Money.krw(0)
        Money.usd(51.58) * 0 shouldBe Money.usd(0)
      }
    }
  }

  describe("Money 객체 나눗셈 시") {
    context("0이 아닌 수로 나누는 경우") {
      it("Money 객체의 금액을 나눈 Money 객체를 반환한다.") {
        Money.krw(10_000) / 2 shouldBe Money.krw(5_000)
        Money.usd(51.58) / 2 shouldBe Money.usd(25.79)
      }
    }

    context("0으로 나누는 경우") {
      it("ArithmeticException이 발생한다.") {
        shouldThrow<ArithmeticException> { Money.krw(10_000) / 0 shouldBe Money.krw(0) }
        shouldThrow<ArithmeticException> { Money.usd(51.58) / 0 shouldBe Money.usd(0) }

      }
    }
  }

  describe("formatted()") {
    it("통화 타입과 금액을 포맷팅한 문자열을 반환한다.") {
      Money.krw(10_000).formatted() shouldBe "KRW 10,000"
      Money.usd(10.266).formatted() shouldBe "USD 10.27"
    }
  }

  describe("compareTo()") {
    context("같은 통화 타입끼리 비교한 경우") {
      val krw1 = Money.krw(10_000)
      val krw2 = Money.krw(5_000)
      val krw3 = Money.krw(10_000)

      it("금액을 비교한 결과를 반환한다.") {
        (krw1 > krw2) shouldBe true
        (krw1 >= krw2) shouldBe true
        (krw2 > krw3) shouldBe false
        (krw2 >= krw3) shouldBe false
        (krw2 < krw1) shouldBe true
        (krw2 <= krw1) shouldBe true
        (krw1 > krw3) shouldBe false
      }
    }

    context("다른 통화 타입끼리 비교한 경우") {
      val krw = Money.krw(10_000)
      val usd = Money.usd(1_000)

      it("CurrencyMissmatchException이 발생한다.") {
        shouldThrow<CurrencyMismatchException> { krw > usd }
        shouldThrow<CurrencyMismatchException> { krw >= usd }
        shouldThrow<CurrencyMismatchException> { krw < usd }
        shouldThrow<CurrencyMismatchException> { krw <= usd }
      }
    }
  }

  describe("isSameCurrency()") {
    context("두 Money 객체의 통화가 같은 경우") {
      it("true를 반환한다.") {
        Money.krw(10_000).isSameCurrency(Money.krw(100)) shouldBe true
      }
    }

    context("두 Money 객체의 통화가 다른 경우") {
      it("false를 반환한다.") {
        Money.krw(10_000).isSameCurrency(Money.usd(100)) shouldBe false
      }
    }
  }
})
