package org.mj.passiveincome.domain.common.email

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class EmailTest {
  @Test
  @DisplayName("이메일 객체 생성 테스트")
  fun createEmailTest() {
    // given
    val email1 = Email("doif.dobby@gmail.com")
    val email2 = Email.of("kjpmj@naver.com")

    // when & then
    assertThat(email1.address).isEqualTo("doif.dobby@gmail.com")
    assertThat(email2.address).isEqualTo("kjpmj@naver.com")
  }

  @Test
  @DisplayName("이메일 invalid email 테스트")
  fun invalidEmail() {
    fun assertThrowInvalidException(block: () -> Unit) {
      org.junit.jupiter.api.assertThrows<InvalidEmailException> { block() }.let { assertThat(it.message).isEqualTo("Invalid email") }
    }

    // given & when & then
    assertThrowInvalidException { Email("doif.dobby") }
    assertThrowInvalidException { Email("doif.dobby@gmail") }
    assertThrowInvalidException { Email("doif.dobby@gmail.c") }
  }
}
