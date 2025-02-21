package org.mj.passiveincome.app.kis

import org.mj.passiveincome.system.data.redis.RedisTimeSeriesCommand
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class RedisTestController(
  private val redisTimeSeriesCommand: RedisTimeSeriesCommand
) {

  @GetMapping("/redis/ts-add")
  fun tsAdd() {
    val timestamp = redisTimeSeriesCommand.tsAdd(
      key = "test",
      value = 123.0,
      retention = 3000
    )
    println("timestamp: $timestamp")
  }

  @GetMapping("/redis/ts-get")
  fun tsGet() {
    val tsGet = redisTimeSeriesCommand.tsGet("test")
    println("tsGet: ${tsGet?.timestamp} ${tsGet?.value}")
  }

  @GetMapping("/redis/ts-rev-range")
  fun tsRevRange() {
    val result = redisTimeSeriesCommand.tsRevRange(
      key = "test",
      fromTimestamp = 1740142859590,
      toTimestamp = 1740144620508
    )
    
    for (timeSeriesValue in result) {
      println("tsRevRange: ${timeSeriesValue.timestamp} ${timeSeriesValue.value}")
    }
  }

}
