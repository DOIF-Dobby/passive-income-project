package org.mj.passiveincome.domain.stock

enum class StockType(
  val typeName: String
) {
  COMMON("보통주"),
  OLD_PREFERRED("구형우선주"),
  NEW_PREFERRED("신형우선주"),
  CLASS_SHARES("종류주권"),
}
