package org.mj.passiveincome.system.web.databind

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * MappingJackson2HttpMessageConverter 에서 사용할 ObjectMapper
 */
class ApiObjectMapper : ObjectMapper() {
  init {
    registerKotlinModule()
    registerModules(JavaTimeModule())

    // enum 컨버팅 시, 알 수 없는 값이 들어오면 null로 처리
    configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)

    // 코틀린 특성 Int -> int / Int? -> Integer 타입이기 때문에 null이 들어오면 0으로 처리 된다.
    // 이를 방지하기 위해 non-null 타입의 primitive 타입에 null이 들어오면 예외를 발생시킨다.
    configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)

    // 역직렬화 할 대상 클래스에 없는 필드가 들어올 경우 허용
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  }
}
