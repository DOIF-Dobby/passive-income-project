package org.mj.passiveincome.domain.common.range

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalTime
import java.time.format.DateTimeParseException

class MinutesRangeTest {

  @Test
  @DisplayName("MinutesRange 생성 테스트")
  fun minutesRangeCreateTest() {
    // given
    val range1 = MinutesRange(LocalTime.of(10, 0), LocalTime.of(10, 10))
    val range2 = MinutesRange.of(LocalTime.of(10, 0), LocalTime.of(10, 10))
    val range3 = MinutesRange.of("10:00", "10:10")

    // when & then
    assertThat(range1.start).isEqualTo(LocalTime.of(10, 0))
    assertThat(range1.end).isEqualTo(LocalTime.of(10, 10))
    assertThat(range1.toString()).isEqualTo("10:00 ~ 10:10")

    assertThat(range2.start).isEqualTo(LocalTime.of(10, 0))
    assertThat(range2.end).isEqualTo(LocalTime.of(10, 10))
    assertThat(range2.toString()).isEqualTo("10:00 ~ 10:10")

    assertThat(range3.start).isEqualTo(LocalTime.of(10, 0))
    assertThat(range3.end).isEqualTo(LocalTime.of(10, 10))
    assertThat(range3.toString()).isEqualTo("10:00 ~ 10:10")
  }

  @Test
  @DisplayName("MinutesRange 동등성 테스트")
  fun test() {
    // given
    val range1 = MinutesRange(LocalTime.of(10, 0), LocalTime.of(10, 10))
    val range2 = MinutesRange.of(LocalTime.of(10, 0), LocalTime.of(10, 10))
    val range3 = MinutesRange.of("10:00", "10:10")

    // when & then
    assertThat(range1).isEqualTo(range2)
    assertThat(range1).isEqualTo(range3)
  }

  @Test
  @DisplayName("MinutesRange 생성 실패 테스트")
  fun minutesRangeCreateThrowExceptionTest() {
    // given & when & then
    assertThrows<InvalidTimeRangeException> { MinutesRange(LocalTime.of(10, 30), LocalTime.of(10, 20)) }
      .let { assertThat(it.message).isEqualTo("Invalid time range") }

    assertThrows<InvalidTimeRangeException> { MinutesRange.of("10:40", "10:30") }
      .let { assertThat(it.message).isEqualTo("Invalid time range") }

    assertThrows<DateTimeParseException> { MinutesRange.of("10-40", "10-50") }
  }

  @Test
  @DisplayName("특정 시간이 범위 내에 있는지 확인")
  fun isWithinRangeTest() {
    // given
    val range = MinutesRange.of("10:00", "10:10")

    // when & then
    assertThat(range.isWithinRange(LocalTime.of(10, 0))).isTrue()
    assertThat(range.isWithinRange(LocalTime.of(10, 5))).isTrue()
    assertThat(range.isWithinRange(LocalTime.of(10, 10))).isTrue()
    assertThat(range.isWithinRange(LocalTime.of(9, 59))).isFalse()
    assertThat(range.isWithinRange(LocalTime.of(10, 11))).isFalse()

    assertThat(range.isWithinRange("10:00")).isTrue()
    assertThat(range.isWithinRange("10:05")).isTrue()
    assertThat(range.isWithinRange("10:10")).isTrue()
    assertThat(range.isWithinRange("09:59")).isFalse()
    assertThat(range.isWithinRange("10:11")).isFalse()

    assertThat(MinutesRange.of("10:00", "10:00").isWithinRange("10:00")).isTrue()
  }

  @Test
  @DisplayName("범위가 겹치는지 확인")
  fun isOverlapRangeTest() {
    // given
    val range = MinutesRange.of("10:00", "10:10")

    // when & then
    assertThat(range.isOverlapRange("09:50", "10:00")).isTrue()
    assertThat(range.isOverlapRange("09:50", "10:05")).isTrue()
    assertThat(range.isOverlapRange("09:50", "10:10")).isTrue()
    assertThat(range.isOverlapRange("09:50", "10:15")).isTrue()

    assertThat(range.isOverlapRange("10:00", "10:00")).isTrue()
    assertThat(range.isOverlapRange("10:00", "10:05")).isTrue()
    assertThat(range.isOverlapRange("10:00", "10:10")).isTrue()
    assertThat(range.isOverlapRange("10:00", "10:15")).isTrue()

    assertThat(range.isOverlapRange("10:05", "10:05")).isTrue()
    assertThat(range.isOverlapRange("10:05", "10:10")).isTrue()
    assertThat(range.isOverlapRange("10:05", "10:15")).isTrue()

    assertThat(range.isOverlapRange("10:10", "10:10")).isTrue()
    assertThat(range.isOverlapRange("10:10", "10:15")).isTrue()

    assertThat(range.isOverlapRange("09:50", "09:59")).isFalse()
    assertThat(range.isOverlapRange("10:11", "10:20")).isFalse()

    assertThat(range.isOverlapRange(MinutesRange.of("10:00", "10:10"))).isTrue()
    assertThat(range.isOverlapRange(MinutesRange.of("10:05", "10:08"))).isTrue()
    assertThat(range.isOverlapRange(MinutesRange.of("09:50", "10:00"))).isTrue()
    assertThat(range.isOverlapRange(MinutesRange.of("09:50", "10:05"))).isTrue()
    assertThat(range.isOverlapRange(MinutesRange.of("10:05", "10:15"))).isTrue()
    assertThat(range.isOverlapRange(MinutesRange.of("10:10", "10:15"))).isTrue()
    assertThat(range.isOverlapRange(MinutesRange.of("09:50", "09:59"))).isFalse()
    assertThat(range.isOverlapRange(MinutesRange.of("10:11", "10:15"))).isFalse()

  }
}
