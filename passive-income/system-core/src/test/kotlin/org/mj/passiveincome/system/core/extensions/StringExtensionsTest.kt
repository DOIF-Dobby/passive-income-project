package org.mj.passiveincome.system.core.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class StringExtensionsTest {

  @Test
  @DisplayName("compact 테스트")
  fun compactTest() {
    // given
    val message = """
      Hello
      World
    """

    // when
    val result = message.compact()

    // then
    assertThat(result).isEqualTo("HelloWorld")
  }
}
