package org.mj.passiveincome.system.core.base

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

class BaseExceptionTest {

  @Test
  @DisplayName("BaseException.localizedMessage 테스트 (KOREAN)")
  fun baseExceptionLocaleKorean() {
    // given
    LocaleContextHolder.setLocale(Locale.KOREAN)

    // when
    val localizedMessage = assertThrows<BaseException> { throw HelloBaseException() }
      .localizedMessage

    // then
    assertThat(localizedMessage).isEqualTo("처리 중 오류가 발생했어요.")
  }

  @Test
  @DisplayName("BaseException.localizedMessage 테스트 (ENGLISH)")
  fun baseExceptionLocaleEnglish() {
    // given
    LocaleContextHolder.setLocale(Locale.ENGLISH)

    // when
    val localizedMessage = assertThrows<BaseException> { throw HelloBaseException() }
      .localizedMessage

    // then
    assertThat(localizedMessage).isEqualTo("An error occurred while processing.")
  }
}

class HelloBaseException : BaseException()
