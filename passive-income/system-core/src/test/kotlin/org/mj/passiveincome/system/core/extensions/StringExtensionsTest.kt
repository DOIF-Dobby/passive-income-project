package org.mj.passiveincome.system.core.extensions

import io.kotest.core.spec.style.DescribeSpec
import org.assertj.core.api.Assertions.assertThat

class StringExtensionsTest : DescribeSpec({

  describe("compact") {
    it("모든 공백, 줆바꿈이 제거된 문자를 반환한다") {
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
})
