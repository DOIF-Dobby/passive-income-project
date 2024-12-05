package org.mj.passiveincome.system.core.base

enum class BaseStatus(
  val code: String,
  val message: String,
) {
  OK("00", "Ok"),
  FAIL("99", "Fail"),
}
