package org.mj.passiveincome.system.core.base

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

class BaseExceptionTest : DescribeSpec({
  describe("BaseException.localizedMessage") {
    context("Locale이 KOREAN일 때") {
      LocaleContextHolder.setLocale(Locale.KOREAN)

      it("한국어로 반환한다.") {
        assertThrows<BaseException> { throw HelloBaseException() }
          .localizedMessage shouldBe "처리 중 오류가 발생했어요."
      }
    }

    context("Locale이 ENGLISH일 때") {
      LocaleContextHolder.setLocale(Locale.ENGLISH)

      it("영어로 반환한다.") {
        assertThrows<BaseException> { throw HelloBaseException() }
          .localizedMessage shouldBe "An error occurred while processing."
      }
    }
  }
})

class HelloBaseException : BaseException()
