package org.mj.passiveincome.domain.common.range

import jakarta.persistence.Embeddable
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Embeddable
class MinutesRange(
  startMinutes: LocalTime,
  endMinutes: LocalTime,
) {

  private val _start = startMinutes.truncatedTo(ChronoUnit.MINUTES)
  private val _end = endMinutes.truncatedTo(ChronoUnit.MINUTES)

  val start: LocalTime
    get() = _start
  val end: LocalTime
    get() = _end

  init {
    requireTimeCondition(_start, _end)
  }

  /**
   * 특정 날짜가 범위 내에 있는지 확인한다.
   */
  fun isWithinRange(time: LocalTime): Boolean {
    val truncatedTime = time.truncatedTo(ChronoUnit.MINUTES)
    
    return truncatedTime in start..end
  }

  fun isWithinRange(time: String): Boolean {
    return isWithinRange(LocalTime.parse(time))
  }

  /**
   * 범위가 겹치는지 확인한다.
   */
  fun isOverlapRange(startTime: LocalTime, endTime: LocalTime): Boolean {
    val truncatedStart = startTime.truncatedTo(ChronoUnit.MINUTES)
    val truncatedEnd = endTime.truncatedTo(ChronoUnit.MINUTES)

    requireTimeCondition(truncatedStart, truncatedEnd)

    return start <= truncatedEnd && end >= truncatedStart
  }

  fun isOverlapRange(startTime: String, endTime: String): Boolean {
    return isOverlapRange(LocalTime.parse(startTime), LocalTime.parse(endTime))
  }

  fun isOverlapRange(minutesRange: MinutesRange): Boolean {
    return isOverlapRange(minutesRange.start, minutesRange.end)
  }


  override fun toString(): String {
    return "$start ~ $end"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as MinutesRange

    if (_start != other._start) return false
    if (_end != other._end) return false

    return true
  }

  override fun hashCode(): Int {
    var result = _start?.hashCode() ?: 0
    result = 31 * result + (_end?.hashCode() ?: 0)
    return result
  }

  companion object {
    fun of(startMinutes: LocalTime, endMinutes: LocalTime): MinutesRange {
      return MinutesRange(startMinutes, endMinutes)
    }

    fun of(startMinutes: String, endMinutes: String): MinutesRange {
      return MinutesRange(LocalTime.parse(startMinutes), LocalTime.parse(endMinutes))
    }

    private fun requireTimeCondition(start: LocalTime, end: LocalTime) {
      require(start.isBefore(end) || start == end) { throw InvalidTimeRangeException() }
    }
  }
}


