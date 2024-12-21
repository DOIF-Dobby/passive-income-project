package org.mj.passiveincome.system.web.databind

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.mj.passiveincome.system.core.extensions.compact

class ApiObjectMapperTest : DescribeSpec({

  describe("readValue()") {
    context("요청 파라미터가 정상적인 경우") {
      val message = """
          {
            "name": "MJ",
            "age": 30,
            "status": "OLD"
          }
        """

      val person = mapper.readValue(message, Person::class.java)

      it("정상적으로 객체로 변환한다") {
        person.name shouldBe "MJ"
        person.age shouldBe 30
        person.status shouldBe PersonStatus.OLD
      }
    }

    context("enum 값을 컨버팅 할 수 없는 경우") {
      val message1 = """
      {
        "name": "MJ",
        "age": 30,
        "status": "HELLO"
      }
      """.compact()

      val message2 = """
      {
        "name": "MJ",
        "age": 30
      }
      """.compact()


      it("MismatchedInputException이 발생한다.") {
        assertThrows<MismatchedInputException> { mapper.readValue(message1, Person::class.java) }
        assertThrows<MismatchedInputException> { mapper.readValue(message2, Person::class.java) }
      }
    }


    /**
     * 코틀린 특성 Int -> int / Int? -> Integer 타입이기 때문에 null이 들어오면 0으로 처리 된다.
     * 이를 방지하기 위해 DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES 를 true로 설정 하였다.
     * 이렇게 하면 Int 타입에 null이 들어오면 0으로 역직렬화 하는 것이 아닌 예외가 발생한다.
     *
     * 물론 null을 허용하려면 Int? 타입으로 설정하면 된다.
     */
    context("primitive 값에 null이 들어온 경우") {
      val message1 = """
      {
        "name": "MJ",
        "status": "OLD"
      }
      """.compact()

      it("MismatchedInputException이 발생한다.") {
        assertThrows<MismatchedInputException> { mapper.readValue(message1, Person::class.java) }
      }
    }
  }

  describe("writeValueAsString()") {
    val person = Person("MJ", 30, PersonStatus.YOUNG)
    val expectedMessage = """
      {
        "name": "MJ",
        "age": 30,
        "status": "YOUNG"
      }
      """.compact()

    it("정상 동작") {
      val message = mapper.writeValueAsString(person)
      message shouldBe expectedMessage
    }
  }

}) {
  companion object {
    private val mapper = ApiObjectMapper()
  }
}

data class Person(
  val name: String,
  val age: Int,
  val status: PersonStatus
)

enum class PersonStatus {
  OLD, YOUNG
}
