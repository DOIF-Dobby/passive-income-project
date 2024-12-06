package org.mj.passiveincome.system.data.redis

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.mj.passiveincome.system.core.logging.logger
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.Duration
import kotlin.reflect.KClass

class RedisCommand(
  redisConnectionFactory: RedisConnectionFactory,
) {

  private val redisTemplate: RedisTemplate<String, String> = StringRedisTemplate(redisConnectionFactory)

  private val objectMapper: ObjectMapper = ObjectMapper().apply {
    registerKotlinModule()
    registerModules(JavaTimeModule())
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
    setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
  }

  fun set(key: String, value: Any, duration: Duration = Duration.ofSeconds(-1)) {
    redisTemplate.opsForValue().set(key, serialize(value), duration)
  }

  fun get(key: String): String? {
    return redisTemplate.opsForValue().get(key)
  }

  fun <T : Any> get(key: String, classType: KClass<T>): T? {
    return deserialize(redisTemplate.opsForValue().get(key), classType)
  }

  private fun serialize(value: Any): String {
    try {
      return objectMapper.writeValueAsString(value)
    } catch (e: Exception) {
      logger.error("Failed to serialize value")
      throw RedisSerializationException()
    }
  }

  private fun <T : Any> deserialize(value: String?, classType: KClass<T>): T? {
    if (value == null) {
      return null
    }

    try {
      return objectMapper.readValue(value, classType.java)
    } catch (e: Exception) {
      logger.error("Failed to deserialize value: $value")
      throw RedisDeserializationException()
    }
  }
}
