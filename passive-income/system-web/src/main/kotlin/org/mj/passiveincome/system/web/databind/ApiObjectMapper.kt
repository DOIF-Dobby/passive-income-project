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

    configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true) // enum 컨버팅 시, 알 수 없는 값이 들어오면 null로 처리
    configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true) // primitive 타입에 null이 들어오면 예외 발생
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  }
}
