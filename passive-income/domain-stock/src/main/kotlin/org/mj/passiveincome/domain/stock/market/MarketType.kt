package org.mj.passiveincome.domain.stock.market

import org.mj.passiveincome.domain.common.country.Country
import org.mj.passiveincome.domain.common.range.MinutesRange
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

enum class MarketType(
  val country: Country,
  val timeZone: ZoneId,
  val regularMarketTimeRange: MinutesRange,
  val preMarketTimeRange: MinutesRange,
  val postMarketTimeRange: MinutesRange? = null,
) {
  KOSPI(
    country = Country.KOREA,
    timeZone = ZoneId.of("Asia/Seoul"),
    regularMarketTimeRange = MinutesRange.of("09:00", "15:30"),
    preMarketTimeRange = MinutesRange.of("08:30", "08:40"),
    postMarketTimeRange = MinutesRange.of("15:40", "16:00"),
  ),
  KOSDAQ(
    country = KOSPI.country,
    timeZone = KOSPI.timeZone,
    regularMarketTimeRange = KOSPI.regularMarketTimeRange,
    preMarketTimeRange = KOSPI.preMarketTimeRange,
    postMarketTimeRange = KOSPI.postMarketTimeRange,
  ),
//  KONEX,
//  KOSDAQ_GLOBAL,

  NASDAQ(
    country = Country.USA,
    timeZone = ZoneId.of("America/New_York"),
    regularMarketTimeRange = MinutesRange.of("09:30", "16:00"), // 23:30 ~ 06:00 TODO: 섬머타임 고려해야 할 듯
    preMarketTimeRange = MinutesRange.of("04:00", "09:30"), // 18:00 ~ 23:30 TODO: KIS 서비스는 프리마켓을 지원하지 않는듯?
  )
  ;

  /**
   * 한국 시간으로 변환한 정규 시장 시작 시간
   */
  val koreanRegularMarketStartTime
    get(): LocalTime {
      val zoneTime = ZonedDateTime.of(
        LocalDate.now(),
        regularMarketTimeRange.start,
        timeZone
      )

      return zoneTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalTime()
    }

  /**
   * 한국 시간으로 변환한 정규 시장 종료 시간
   */
  val koreanRegularMarketEndTime
    get(): LocalTime {
      val zoneTime = ZonedDateTime.of(
        LocalDate.now(),
        regularMarketTimeRange.end,
        timeZone
      )

      return zoneTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalTime()
    }

  /**
   * 한국 시간으로 변환한 장전 시장 시작 시간
   */
  val koreanPreMarketStartTime
    get(): LocalTime {
      val zoneTime = ZonedDateTime.of(
        LocalDate.now(),
        preMarketTimeRange.start,
        timeZone
      )

      return zoneTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalTime()
    }

  /**
   * 한국 시간으로 변환한 장전 시장 종료 시간
   */
  val koreanPreMarketEndTime
    get(): LocalTime {
      val zoneTime = ZonedDateTime.of(
        LocalDate.now(),
        preMarketTimeRange.end,
        timeZone
      )

      return zoneTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalTime()
    }

  /**
   * 한국 시간으로 변환한 장외 시장 시작 시간
   */
  val koreanPostMarketStartTime
    get(): LocalTime? {
      postMarketTimeRange ?: return null

      val zoneTime = ZonedDateTime.of(
        LocalDate.now(),
        postMarketTimeRange.start,
        timeZone
      )

      return zoneTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalTime()
    }

  /**
   * 한국 시간으로 변환한 장외 시장 종료 시간
   */
  val koreanPostMarketEndTime
    get(): LocalTime? {
      postMarketTimeRange ?: return null

      val zoneTime = ZonedDateTime.of(
        LocalDate.now(),
        postMarketTimeRange.end,
        timeZone
      )

      return zoneTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalTime()
    }

  /**
   * 한국 시간으로 변환한 마켓 오픈 시간 List
   */
  val koreanMarketOpenTimes
    get() : List<MinutesRange> {
      val regularMarketTimes = if (koreanRegularMarketStartTime > koreanRegularMarketEndTime) {
        mutableListOf(
          MinutesRange.of(koreanRegularMarketStartTime, LocalTime.MAX),
          MinutesRange.of(LocalTime.MIN, koreanRegularMarketEndTime)
        )
      } else {
        mutableListOf(MinutesRange.of(koreanRegularMarketStartTime, koreanRegularMarketEndTime))
      }

      val preMarketTimes = if (koreanPreMarketStartTime > koreanPreMarketEndTime) {
        mutableListOf(
          MinutesRange.of(koreanPreMarketStartTime, LocalTime.MAX),
          MinutesRange.of(LocalTime.MIN, koreanPreMarketEndTime)
        )
      } else {
        mutableListOf(MinutesRange.of(koreanPreMarketStartTime, koreanPreMarketEndTime))
      }

      if (postMarketTimeRange == null) {
        return regularMarketTimes + preMarketTimes
      }

      val postMarketTimes = if (koreanPostMarketStartTime!! > koreanPostMarketEndTime!!) {
        mutableListOf(
          MinutesRange.of(koreanPostMarketStartTime!!, LocalTime.MAX),
          MinutesRange.of(LocalTime.MIN, koreanPostMarketEndTime!!)
        )
      } else {
        mutableListOf(
          MinutesRange.of(koreanPostMarketStartTime!!, koreanPostMarketEndTime!!)
        )
      }

      return regularMarketTimes + preMarketTimes + postMarketTimes
    }


  fun isMarketOpen(): Boolean {
    return isMarketOpen(LocalTime.now())
  }

  fun isMarketOpen(time: LocalTime): Boolean {
    return koreanMarketOpenTimes.any { it.isWithinRange(time) }
  }
}
