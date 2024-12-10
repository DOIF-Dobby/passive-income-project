package org.mj.passiveincome.system.core.base

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

class BaseResponseTest {

  @Test
  @DisplayName("BaseResponse 테스트 (KOREAN)")
  fun baseResponseKorean() {
    // given
    LocaleContextHolder.setLocale(Locale.KOREAN)

    val ok = BaseResponse.ok()
    val fail = BaseResponse.fail()

    // then
    assertThat(ok.code).isEqualTo("00")
    assertThat(ok.message).isEqualTo("정상적으로 처리 되었어요.")

    assertThat(fail.code).isEqualTo("99")
    assertThat(fail.message).isEqualTo("처리 중 오류가 발생했어요.")
  }

  @Test
  @DisplayName("BaseResponse 테스트 (ENGLISH)")
  fun baseResponseEnglish() {
    // given
    LocaleContextHolder.setLocale(Locale.ENGLISH)

    val ok = BaseResponse.ok()
    val fail = BaseResponse.fail()

    // then
    assertThat(ok.code).isEqualTo("00")
    assertThat(ok.message).isEqualTo("Successfully processed.")

    assertThat(fail.code).isEqualTo("99")
    assertThat(fail.message).isEqualTo("An error occurred while processing.")
  }
}
