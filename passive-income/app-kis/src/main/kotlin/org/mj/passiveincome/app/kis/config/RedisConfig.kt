package org.mj.passiveincome.app.kis.config

import org.mj.passiveincome.system.data.redis.RedisCommand
import org.mj.passiveincome.system.data.redis.RedisTimeSeriesCommand
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfig {

  @Bean
  fun redisCommand(redisConnectionFactory: RedisConnectionFactory): RedisCommand {
    return RedisCommand(redisConnectionFactory)
  }

  @Bean
  fun redisTimeSeriesCommand(lettuceConnectionFactory: LettuceConnectionFactory): RedisTimeSeriesCommand {
    return RedisTimeSeriesCommand(lettuceConnectionFactory)
  }
}
