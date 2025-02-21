package org.mj.passiveincome.system.data.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.api.sync.RedisCommands
import io.lettuce.core.codec.StringCodec
import io.lettuce.core.output.ArrayOutput
import io.lettuce.core.output.IntegerOutput
import io.lettuce.core.protocol.CommandArgs
import io.lettuce.core.protocol.ProtocolKeyword
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.nio.charset.StandardCharsets

class RedisTimeSeriesCommand(
  private val lettuceConnectionFactory: LettuceConnectionFactory
) {

  private fun getCommand(): RedisCommands<String, String> {
    val redisClient = lettuceConnectionFactory.nativeClient as RedisClient
    val connection = redisClient.connect()
    return connection.sync()
  }

  /**
   * Add TimeSeries value
   */
  fun tsAdd(key: String, value: Double, timestamp: Long? = null, retention: Long? = null): Long {
    val args = CommandArgs(StringCodec.UTF8)
      .add(key)
      .add(timestamp?.toString() ?: "*")
      .add(value)

    if (retention != null) {
      args.add("RETENTION")
      args.add(retention.toString())
    }

    val command = getCommand()
    val output = IntegerOutput(StringCodec.UTF8)
    return command.dispatch(TS_ADD, output, args)
  }


  /**
   * Get TimeSeries value
   */
  fun tsGet(key: String): TimeSeriesValue? {
    val args = CommandArgs(StringCodec.UTF8)
      .add(key)

    val command = getCommand()
    val output = ArrayOutput(StringCodec.UTF8)
    val result = command.dispatch(TS_GET, output, args) as? List<*> ?: return null

    if (result.isEmpty()) {
      return null
    }

    val timestamp = result[0] as Long
    val value = result[1] as Double

    return TimeSeriesValue(timestamp, value)
  }

  /**
   * Get Range TimeSeries value
   */
  fun tsRevRange(key: String, fromTimestamp: Long? = null, toTimestamp: Long? = null): List<TimeSeriesValue> {
    val args = CommandArgs(StringCodec.UTF8)
      .add(key)
      .add(fromTimestamp?.toString() ?: "-")
      .add(toTimestamp?.toString() ?: "+")

    val command = getCommand()
    val output = ArrayOutput(StringCodec.UTF8)
    val result = command.dispatch(TS_REV_RANGE, output, args) as? List<*>
      ?: return emptyList()

    val values = result
      .stream()
      .map { tsValue ->
        tsValue as List<*>
        val timestamp = tsValue[0] as Long
        val value = tsValue[1] as Double
        TimeSeriesValue(timestamp, value)
      }
      .toList()

    return values
  }

  companion object {
    private val TS_ADD = object : ProtocolKeyword {
      override fun getBytes(): ByteArray = "TS.ADD".toByteArray(StandardCharsets.UTF_8)
      override fun name(): String = "TS.ADD"
    }

    private val TS_GET = object : ProtocolKeyword {
      override fun getBytes(): ByteArray = "TS.GET".toByteArray(StandardCharsets.UTF_8)
      override fun name(): String = "TS.GET"
    }

    private val TS_REV_RANGE = object : ProtocolKeyword {
      override fun getBytes(): ByteArray = "TS.REVRANGE".toByteArray(StandardCharsets.UTF_8)
      override fun name(): String = "TS.REVRANGE"
    }
  }
}
