package org.mj.passiveincome.app.kis.config

import org.mj.passiveincome.system.data.redis.RedisCommand
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory

@Configuration
class RedisConfig {

  @Bean
  fun redisCommand(redisConnectionFactory: RedisConnectionFactory): RedisCommand {
    return RedisCommand(redisConnectionFactory)
  }
}
