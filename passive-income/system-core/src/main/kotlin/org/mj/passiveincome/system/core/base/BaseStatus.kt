package org.mj.passiveincome.system.core.base

enum class BaseStatus(
  val code: String,
  val message: String,
) {
  SUCCESS("00", "Success"),
  FAIL("99", "Fail"),
}
