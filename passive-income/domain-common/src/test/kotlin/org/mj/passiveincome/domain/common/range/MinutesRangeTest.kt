package org.mj.passiveincome.domain.common.range

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.DateTimeException
import java.time.LocalTime
import java.time.format.DateTimeParseException

class MinutesRangeTest : DescribeSpec({

  describe("MinutesRange 객체 생성") {
    context("시간이 정상적인 경우") {
      it("정상적으로 객체을 생성한다.") {
        val range1 = MinutesRange(LocalTime.of(10, 0), LocalTime.of(10, 10))
        val range2 = MinutesRange.of(LocalTime.of(10, 0), LocalTime.of(10, 10))
        val range3 = MinutesRange.of("10:00", "10:10")

        range1.start shouldBe LocalTime.of(10, 0)
        range1.end shouldBe LocalTime.of(10, 10)
        range1.toString() shouldBe "10:00 ~ 10:10"

        range2.start shouldBe LocalTime.of(10, 0)
        range2.end shouldBe LocalTime.of(10, 10)
        range2.toString() shouldBe "10:00 ~ 10:10"

        range3.start shouldBe LocalTime.of(10, 0)
        range3.end shouldBe LocalTime.of(10, 10)
        range3.toString() shouldBe "10:00 ~ 10:10"
      }
    }

    context("시작 시간이 종료 시간보다 늦은 경우") {
      it("InvalidTimeRangeException이 발생한다.") {
        shouldThrow<InvalidTimeRangeException> {
          MinutesRange(LocalTime.of(10, 30), LocalTime.of(10, 20))
        }.message shouldBe "Invalid time range"

        shouldThrow<InvalidTimeRangeException> { MinutesRange.of("10:40", "10:30") }
          .message shouldBe "Invalid time range"
      }
    }

    context("시간 형식이 잘못된 경우") {
      it("Exception이 발생한다.") {
        shouldThrow<DateTimeParseException> { MinutesRange.of("10-40", "10-50") }
        shouldThrow<DateTimeException> {
          MinutesRange.of(LocalTime.of(23, 50), LocalTime.of(25, 30))
        }
      }
    }
  }

  describe("isWithinRange()") {
    val range1 = MinutesRange.of("10:00", "10:10")
    val range2 = MinutesRange.of("10:00", "10:00")

    context("비교하는 대상 시간이 범위 내에 있는 경우") {
      it("true를 반환한다.") {
        range1.isWithinRange(LocalTime.of(10, 0)) shouldBe true
        range1.isWithinRange(LocalTime.of(10, 5)) shouldBe true
        range1.isWithinRange(LocalTime.of(10, 10)) shouldBe true
        range1.isWithinRange("10:05") shouldBe true

        range2.isWithinRange("10:00") shouldBe true
      }
    }

    context("비교하는 대상 시간이 범위 밖에 있는 경우") {
      it("false를 반환한다.") {
        range1.isWithinRange(LocalTime.of(9, 59)) shouldBe false
        range1.isWithinRange(LocalTime.of(10, 11)) shouldBe false
        range1.isWithinRange("10:50") shouldBe false

        range2.isWithinRange("10:01") shouldBe false
      }
    }
  }

  describe("isOverlapRange()") {
    val range = MinutesRange.of("10:00", "10:10")

    context("기간이 겹치는 경우") {
      it("true를 반환한다.") {
        range.isOverlapRange("09:50", "10:00") shouldBe true
        range.isOverlapRange("09:58", "10:05") shouldBe true
        range.isOverlapRange("09:50", "10:10") shouldBe true
        range.isOverlapRange("09:50", "10:15") shouldBe true

        range.isOverlapRange("10:00", "10:00") shouldBe true
        range.isOverlapRange("10:00", "10:05") shouldBe true
        range.isOverlapRange("10:00", "10:10") shouldBe true
        range.isOverlapRange("10:00", "10:15") shouldBe true

        range.isOverlapRange("10:05", "10:05") shouldBe true
        range.isOverlapRange("10:05", "10:10") shouldBe true
        range.isOverlapRange("10:05", "10:15") shouldBe true

        range.isOverlapRange("10:10", "10:10") shouldBe true
        range.isOverlapRange("10:10", "10:15") shouldBe true
      }
    }

    context("기간이 겹치지 않는 경우") {
      it("false를 반환한다.") {
        range.isOverlapRange("09:00", "09:50") shouldBe false
        range.isOverlapRange("09:50", "09:59") shouldBe false
        range.isOverlapRange("10:11", "10:15") shouldBe false
      }
    }
  }
})
