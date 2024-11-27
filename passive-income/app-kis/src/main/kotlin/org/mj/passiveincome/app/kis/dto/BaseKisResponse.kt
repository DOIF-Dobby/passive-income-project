package org.mj.passiveincome.app.kis.dto

open class BaseKisResponse<T>(
  val output: T,
  val rtCd: String,
  val msgCd: String,
  val msg1: String,
) {
}
