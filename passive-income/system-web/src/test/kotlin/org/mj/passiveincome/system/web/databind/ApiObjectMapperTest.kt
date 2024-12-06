package org.mj.passiveincome.system.web.databind

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ApiObjectMapperTest {

  private val mapper = ApiObjectMapper()

  @Test
  @DisplayName("ApiObjectMapper readValue 테스트")
  fun readValueTest() {
    // given
    val message = "{\"name\":\"MJ\",\"age\":30,\"status\":\"YOUNG\"}"

    // when
    val person = mapper.readValue(message, Person::class.java)

    // then
    assertThat(person.name).isEqualTo("MJ")
    assertThat(person.age).isEqualTo(30)
    assertThat(person.status).isEqualTo(PersonStatus.YOUNG)
  }

  @Test
  @DisplayName("ApiObjectMapper writeValueAsString 테스트")
  fun writeValueAsStringTest() {
    // given
    val person = Person("MJ", 30, PersonStatus.YOUNG)

    // when
    val message = mapper.writeValueAsString(person)

    // then
    assertThat(message).isEqualTo("{\"name\":\"MJ\",\"age\":30,\"status\":\"YOUNG\"}")
  }

  @Test
  @DisplayName("readValue 시, enum 값을 컨버팅 할 수 없는 경우 예외 발생 테스트")
  fun whenReadValueEnumConvertFail() {
    // given
    val message1 = "{\"name\":\"MJ\",\"age\":30,\"status\":\"HELLO\"}"
    val message2 = "{\"name\":\"MJ\",\"age\":30}"

    // when & then
    assertThrows<MismatchedInputException> { mapper.readValue(message1, Person::class.java) }
    assertThrows<MismatchedInputException> { mapper.readValue(message2, Person::class.java) }
  }

  /**
   * 코틀린 특성 Int -> int / Int? -> Integer 타입이기 때문에 null이 들어오면 0으로 처리 된다.
   * 이를 방지하기 위해 DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES 를 true로 설정 하였다.
   * 이렇게 하면 Int 타입에 null이 들어오면 0으로 역직렬화 하는 것이 아닌 예외가 발생한다.
   *
   * 물론 null을 허용하려면 Int? 타입으로 설정하면 된다.
   */
  @Test
  @DisplayName("readValue 시, primitive 값에 null이 들어오는 경우 예외 발생 테스트")
  fun whenReadValuePrimitiveNull() {
    // given
    val message1 = "{\"name\":\"MJ\",\"status\":\"OLD\"}"

    // when & then
    assertThrows<MismatchedInputException> { mapper.readValue(message1, Person::class.java) }
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
