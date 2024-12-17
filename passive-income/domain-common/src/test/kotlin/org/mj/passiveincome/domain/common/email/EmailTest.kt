package org.mj.passiveincome.domain.common.email

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class EmailTest : DescribeSpec({
  describe("Email 정상 동작 테스트") {
    val address1 = "doif.dobby@gmail.com"
    val address2 = "kjpmj@naver.com"

    context("이메일 객체 생성 시") {
      val email1 = Email(address1)
      val email2 = Email.of(address2)

      it("이메일 객체의 주소가 주어진 주소와 같아야 한다") {
        email1.address shouldBe address1
        email2.address shouldBe address2
      }
    }

    context("같은 주소의 이메일 객체인 경우") {
      val email1 = Email.of(address1)
      val email2 = Email.of(address1)

      it("이메일 객체는 동등해야 한다") {
        email1 shouldBe email2
      }
    }
  }

  describe("Email 예외 동작 테스트") {
    val invalidAddress1 = "doif.dobby"
    val invalidAddress2 = "doif.dobby@gmail"
    val invalidAddress3 = "doif.dobby@gmail.c"

    fun assertThrowInvalidException(block: () -> Unit) {
      shouldThrow<InvalidEmailException> { block() }.message shouldBe "Invalid email"
    }

    context("유효하지 않은 이메일 주소인 경우") {
      it("InvalidEmailException이 발생해야 한다") {
        assertThrowInvalidException { Email(invalidAddress1) }
        assertThrowInvalidException { Email(invalidAddress2) }
        assertThrowInvalidException { Email(invalidAddress3) }
      }
    }
  }
})
