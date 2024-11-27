package org.mj.passiveincome.system.core.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

val loggerCache = ConcurrentHashMap<Class<*>, Logger>()

inline val <reified T> T.logger: Logger
  get() {
    return loggerCache.getOrPut(T::class.java) {
      LoggerFactory.getLogger(T::class.java)
    }
  }

fun Logger.trace(message: () -> String) {
  if (isTraceEnabled) {
    trace(message())
  }
}

fun Logger.debug(message: () -> String) {
  if (isDebugEnabled) {
    debug(message())
  }
}

fun Logger.info(message: () -> String) {
  if (isInfoEnabled) {
    info(message())
  }
}


fun Logger.warn(message: () -> String) {
  if (isWarnEnabled) {
    warn(message())
  }
}

fun Logger.error(message: () -> String) {
  if (isErrorEnabled) {
    error(message())
  }
}
