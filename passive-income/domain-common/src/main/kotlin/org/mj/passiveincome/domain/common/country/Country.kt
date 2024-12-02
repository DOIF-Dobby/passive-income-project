package org.mj.passiveincome.domain.common.country

enum class Country(
  val displayName: String,
  val isoCode: String
) {
  KOREA("대한민국", "KR"),
  USA("미국", "US"),
  ;
}
