package org.mj.passiveincome.system.core.base

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

class BaseResponseTest : DescribeSpec({

  describe("BaseResponse") {
    context("Locale이 KOREAN인 경우") {
      LocaleContextHolder.setLocale(Locale.KOREAN)
      val ok = BaseResponse.ok()
      val fail = BaseResponse.fail()

      it("한국어로 응답한다.") {
        ok.code shouldBe "00"
        ok.message shouldBe "정상적으로 처리 되었어요."

        fail.code shouldBe "99"
        fail.message shouldBe "처리 중 오류가 발생했어요."
      }
    }

    context("Locale이 ENGLISH인 경우") {
      LocaleContextHolder.setLocale(Locale.ENGLISH)
      val ok = BaseResponse.ok()
      val fail = BaseResponse.fail()

      it("영어로 응답한다.") {
        ok.code shouldBe "00"
        ok.message shouldBe "Successfully processed."

        fail.code shouldBe "99"
        fail.message shouldBe "An error occurred while processing."
      }
    }
  }
})
