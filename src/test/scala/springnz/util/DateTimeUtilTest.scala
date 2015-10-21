package springnz.util

import java.util.Date

import org.joda.time.DateTime
import org.scalatest._
import springnz.util.Pimpers._

class DateTimeUtilTest extends WordSpec with ShouldMatchers {

  "DateTimeUtil" should {

    "add compare method to OffsetDateTime" in {
      val utcTime = DateTimeUtil.utcOffsetDateTime
      val utcTimeMinus1 = utcTime.minusSeconds(1)
      val utcTimePlus1 = utcTime.plusSeconds(1)

      utcTime.compare(utcTimeMinus1) shouldBe 1
      utcTime.compare(utcTimePlus1) shouldBe -1
    }

    "convert OffsetDateTime to legacy date" in {
      val utcOffsetDateTime = DateTimeUtil.utcOffsetDateTime
      val legacyDate = utcOffsetDateTime.toLegacyDate
      utcOffsetDateTime.toInstant shouldBe legacyDate.toInstant
    }

    "convert legacy date to OffsetDateTime" in {
      val legacyDate = new Date
      val utcOffsetDateTime = legacyDate.toUtcOffsetDateTime
      legacyDate.toInstant shouldBe utcOffsetDateTime.toInstant
    }

    "convert OffsetDateTime to joda time" in {
      val utcOffsetDateTime = DateTimeUtil.utcOffsetDateTime
      val jodaTime = utcOffsetDateTime.toJodaTime
      jodaTime.toInstant.toString shouldBe utcOffsetDateTime.toInstant.toString
    }

    "convert joda time to OffsetDateTime" in {
      val jodaTime = DateTime.now
      val utcOffsetDateTime = jodaTime.toUtcOffsetDateTime
      utcOffsetDateTime.toInstant.toString shouldBe jodaTime.toInstant.toString
    }

    "convert iso date string to/from LocalDate" in {
      val isoDate = "2014-03-01"
      val localDate = DateTimeUtil.isoDateToLocalDate(isoDate)
      val result = DateTimeUtil.toIsoDate(localDate)
      result shouldBe isoDate
    }

    "convert iso datetime string to/from LocalDateTime" in {
      val isoDateTime = "2014-03-01 13:34:15"
      val localDateTime = DateTimeUtil.isoDateTimeToLocalDateTime(isoDateTime)
      val result = DateTimeUtil.toIsoDateTime(localDateTime)
      result shouldBe isoDateTime
    }
  }

  "DateTime pimped conversions" should {

    "convert LocalDateTime to/from legacy date" in {
      val localDateTime = DateTimeUtil.isoDateTimeToLocalDateTime("2015-10-22 13:01:02")
      val legacyDate = localDateTime.toNZLegacyDate
      legacyDate.toString shouldBe "Thu Oct 22 13:01:02 NZDT 2015"
      val convertedLocalDateTime = legacyDate.toNZLocalDateTime
      convertedLocalDateTime shouldBe localDateTime
    }

    "convert LocalDate to/from legacy date" in {
      val localDate = DateTimeUtil.isoDateToLocalDate("2015-10-22")
      val legacyDate = localDate.toNZLegacyDate
      legacyDate.toString shouldBe "Thu Oct 22 00:00:00 NZDT 2015"
      val convertedLocalDate = legacyDate.toNZLocalDate
      convertedLocalDate shouldBe localDate
    }
  }
}
