package org.mj.passiveincome.domain.stock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mj.passiveincome.domain.common.range.MinutesRange
import org.mj.passiveincome.domain.stock.market.MarketType
import java.time.LocalTime

class MarketTypeTest {

  @Test
  @DisplayName("거래소별 한국 시간 테스트")
  fun koreanTimeTest() {
    // given
    val kospi = MarketType.KOSPI
    val kosdaq = MarketType.KOSDAQ
    val nasdaq = MarketType.NASDAQ

    // when & then
    // KOSPI
    assertThat(kospi.koreanRegularMarketStartTime).isEqualTo(LocalTime.of(9, 0))
    assertThat(kospi.koreanRegularMarketEndTime).isEqualTo(LocalTime.of(15, 30))
    assertThat(kospi.koreanPreMarketStartTime).isEqualTo(LocalTime.of(8, 30))
    assertThat(kospi.koreanPreMarketEndTime).isEqualTo(LocalTime.of(8, 40))
    assertThat(kospi.koreanPostMarketStartTime).isEqualTo(LocalTime.of(15, 40))
    assertThat(kospi.koreanPostMarketEndTime).isEqualTo(LocalTime.of(16, 0))

    // KOSDAQ
    assertThat(kosdaq.koreanRegularMarketStartTime).isEqualTo(LocalTime.of(9, 0))
    assertThat(kosdaq.koreanRegularMarketEndTime).isEqualTo(LocalTime.of(15, 30))
    assertThat(kosdaq.koreanPreMarketStartTime).isEqualTo(LocalTime.of(8, 30))
    assertThat(kosdaq.koreanPreMarketEndTime).isEqualTo(LocalTime.of(8, 40))
    assertThat(kosdaq.koreanPostMarketStartTime).isEqualTo(LocalTime.of(15, 40))
    assertThat(kosdaq.koreanPostMarketEndTime).isEqualTo(LocalTime.of(16, 0))

    // NASDAQ
    assertThat(nasdaq.koreanRegularMarketStartTime).isEqualTo(LocalTime.of(23, 30))
    assertThat(nasdaq.koreanRegularMarketEndTime).isEqualTo(LocalTime.of(6, 0))
    assertThat(nasdaq.koreanPreMarketStartTime).isEqualTo(LocalTime.of(18, 0))
    assertThat(nasdaq.koreanPreMarketEndTime).isEqualTo(LocalTime.of(23, 30))
    assertThat(nasdaq.koreanPostMarketStartTime).isNull()
    assertThat(nasdaq.koreanPostMarketEndTime).isNull()

  }

  @Test
  @DisplayName("한국 시간 기준 오픈 시간 테스트")
  fun koreanOpenTimesTest() {
    // given
    val kospi = MarketType.KOSPI
    val kosdaq = MarketType.KOSDAQ
    val nasdaq = MarketType.NASDAQ

    // when & then
    // KOSPI
    assertThat(kospi.koreanMarketOpenTimes)
      .containsExactlyInAnyOrder(
        MinutesRange.of("09:00", "15:30"),
        MinutesRange.of("08:30", "08:40"),
        MinutesRange.of("15:40", "16:00")
      )

    // KOSDAQ
    assertThat(kosdaq.koreanMarketOpenTimes)
      .containsExactlyInAnyOrder(
        MinutesRange.of("09:00", "15:30"),
        MinutesRange.of("08:30", "08:40"),
        MinutesRange.of("15:40", "16:00")
      )

    // NASDAQ
    assertThat(nasdaq.koreanMarketOpenTimes)
      .containsExactlyInAnyOrder(
        MinutesRange.of("18:00", "23:30"),
        MinutesRange.of("23:30", "23:59"),
        MinutesRange.of("00:00", "06:00")
      )
  }

  @Test
  @DisplayName("거래소별 open 체크 테스트")
  fun isOpenMarketTest() {
    // given
    val kospi = MarketType.KOSPI
    val kosdaq = MarketType.KOSDAQ
    val nasdaq = MarketType.NASDAQ

    // when & then
    // KOSPI
    assertThat(kospi.isMarketOpen(LocalTime.of(8, 0))).isFalse()
    assertThat(kospi.isMarketOpen(LocalTime.of(8, 35))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(8, 40))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(8, 50))).isFalse()

    assertThat(kospi.isMarketOpen(LocalTime.of(9, 0))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(10, 0))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(12, 30))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(15, 30))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(15, 35))).isFalse()

    assertThat(kospi.isMarketOpen(LocalTime.of(15, 40))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(15, 50))).isTrue()
    assertThat(kospi.isMarketOpen(LocalTime.of(16, 0))).isTrue()

    assertThat(kospi.isMarketOpen(LocalTime.of(16, 10))).isFalse()
    assertThat(kospi.isMarketOpen(LocalTime.of(18, 10))).isFalse()

    // KOSDAQ
    assertThat(kosdaq.isMarketOpen(LocalTime.of(8, 0))).isFalse()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(8, 35))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(8, 40))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(8, 50))).isFalse()

    assertThat(kosdaq.isMarketOpen(LocalTime.of(9, 0))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(10, 0))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(12, 30))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(15, 30))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(15, 35))).isFalse()

    assertThat(kosdaq.isMarketOpen(LocalTime.of(15, 40))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(15, 50))).isTrue()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(16, 0))).isTrue()

    assertThat(kosdaq.isMarketOpen(LocalTime.of(16, 10))).isFalse()
    assertThat(kosdaq.isMarketOpen(LocalTime.of(18, 10))).isFalse()

    // NASDAQ
    assertThat(nasdaq.isMarketOpen(LocalTime.of(18, 0))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(21, 0))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(23, 30))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(23, 50))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(0, 0))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(0, 30))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(3, 30))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(5, 30))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(6, 0))).isTrue()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(7, 0))).isFalse()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(9, 0))).isFalse()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(12, 0))).isFalse()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(15, 0))).isFalse()
    assertThat(nasdaq.isMarketOpen(LocalTime.of(17, 59))).isFalse()

  }
}
