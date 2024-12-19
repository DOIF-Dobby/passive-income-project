package org.mj.passiveincome.domain.common.email

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class EmailTest : DescribeSpec({

  describe("Email 객체 생성 시") {
    context("이메일 주소가 유효한 경우") {
      val email1 = Email("doif.dobby@gmail.com")
      val email2 = Email.of("kjpmj@naver.com")

      it("주어진 주소를 갖는 Email 객체를 생성한다.") {
        email1.address shouldBe "doif.dobby@gmail.com"
        email2.address shouldBe "kjpmj@naver.com"
      }
    }

    context("이메일 주소가 유효하지 않은 경우") {
      it("InvalidEmailException이 발생한다.") {
        assertThrowInvalidException { Email.of("doif.dobby") }
        assertThrowInvalidException { Email.of("doif.dobby@gmail") }
        assertThrowInvalidException { Email.of("doif.dobby@gmail.c") }
      }
    }

  }

  
  describe("Email 객체 비교 시") {
    context("이메일 주소가 같은 경우") {
      val email1 = Email("doif.dobby@gmail.com")
      val email2 = Email.of("doif.dobby@gmail.com")

      it("두 Email 객체는 동등하다.") {
        email1 shouldBe email2
      }
    }

    context("이메일 주소가 같지 않은 경우") {
      val email1 = Email("doif.dobby@gmail.com")
      val email2 = Email.of("kjpmj@naver.com")

      it("두 Email 객체는 동등하지 않다.") {
        email1 shouldNotBe email2
      }
    }
  }
}) {
  companion object {
    fun assertThrowInvalidException(block: () -> Unit) {
      shouldThrow<InvalidEmailException> { block() }.message shouldBe "Invalid email"
    }
  }
}
