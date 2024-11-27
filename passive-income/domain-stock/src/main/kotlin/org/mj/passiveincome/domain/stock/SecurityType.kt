package org.mj.passiveincome.domain.stock

enum class SecurityType(
  val typeName: String
) {
  EQUITY("주권"),
  REIT("부동산투자회사"),
  DEPOSITORY_RECEIPT("주식예탁증권"),
  FOREIGN_EQUITY("외국주권"),
  SOC_INFRA_INVESTMENT("사회간접자본투융자회사"),
  INVESTMENT_COMPANY("투자회사"),
}
