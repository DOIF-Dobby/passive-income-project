package org.mj.passiveincome.system.core.extensions

/**
 * 문자열 공백 / 탭 / 개행 제거
 */
fun String.compact(): String {
  return this.trimIndent()
    .replace(System.lineSeparator(), "")
    .replace(" ", "")
}
