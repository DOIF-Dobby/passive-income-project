package org.mj.passiveincome.system.core.extensions

fun Map<String, String>.toQueryParams(): String {
  return this.entries.joinToString("&") { (key, value) ->
    "${key}=${value}"
  }
}
