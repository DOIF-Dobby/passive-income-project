package org.mj.passiveincome.system.data.redis

import org.mj.passiveincome.system.core.base.BaseException

class RedisSerializationException : BaseException(message = "Redis serialization error")

class RedisDeserializationException : BaseException(message = "Redis deserialization error")
