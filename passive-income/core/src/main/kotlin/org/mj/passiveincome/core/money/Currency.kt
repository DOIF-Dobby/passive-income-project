package org.mj.passiveincome.core.money

import java.math.RoundingMode
import java.text.DecimalFormat

enum class Currency(
  val scale: Int,
  val roundingMode: RoundingMode,
  val decimalFormat: DecimalFormat
) {
  KRW(0, RoundingMode.DOWN, DecimalFormat("#,###")),
  USD(2, RoundingMode.HALF_UP, DecimalFormat("#,##0.00")),
}