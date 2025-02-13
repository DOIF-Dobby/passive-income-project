package org.mj.passiveincome.system.kafka

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.mj.passiveincome.system.core.logging.log
import org.springframework.kafka.core.KafkaTemplate
import kotlin.reflect.KClass

class KafkaCommand(
  private val kafkaTemplate: KafkaTemplate<String, String>
) {

  private val objectMapper: ObjectMapper = ObjectMapper().apply {
    registerKotlinModule()
    registerModules(JavaTimeModule())
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
    setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
  }

  fun send(topic: String, data: Any) {
    kafkaTemplate.send(topic, serialize(data))
  }

  private fun serialize(value: Any): String {
    try {
      return objectMapper.writeValueAsString(value)
    } catch (e: Exception) {
      log.error("Failed to serialize value")
      throw KafkaSerializationException()
    }
  }

  fun <T : Any> deserialize(value: String, classType: KClass<T>): T {
    try {
      return objectMapper.readValue(value, classType.java)
    } catch (e: Exception) {
      log.error("Failed to deserialize value: $value")
      throw KafkaDeserializationException()
    }
  }
}
