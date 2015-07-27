package ylabs.util

import java.util.Date

import org.joda.time.DateTime
import org.scalatest._
import ylabs.util.Pimpers._

class DateTimeUtilTest extends WordSpec with ShouldMatchers {

  "DateTimeUtil" should {

    "add compare method to OffsetDateTime" in {
      val utcTime = DateTimeUtil.utcDateTime
      val utcTimeMinus1 = utcTime.minusSeconds(1)
      val utcTimePlus1 = utcTime.plusSeconds(1)

      utcTime.compare(utcTimeMinus1) shouldBe 1
      utcTime.compare(utcTimePlus1) shouldBe -1
    }

    "convert OffsetDateTime to legacy date" in {
      val utcOffsetDateTime = DateTimeUtil.utcDateTime
      val legacyDate = utcOffsetDateTime.toLegacyDate
      utcOffsetDateTime.toInstant shouldBe legacyDate.toInstant
    }

    "convert legacy date to OffsetDateTime" in {
      val legacyDate = new Date
      val utcOffsetDateTime = legacyDate.toUtcOffsetDateTime
      legacyDate.toInstant shouldBe utcOffsetDateTime.toInstant
    }

    "convert OffsetDateTime to joda time" in {
      val utcOffsetDateTime = DateTimeUtil.utcDateTime
      val jodaTime = utcOffsetDateTime.toJodaTime
      jodaTime.toInstant.toString shouldBe utcOffsetDateTime.toInstant.toString
    }

    "convert joda time to OffsetDateTime" in {
      val jodaTime = DateTime.now
      val utcOffsetDateTime = jodaTime.toUtcOffsetDateTime
      utcOffsetDateTime.toInstant.toString shouldBe jodaTime.toInstant.toString
    }
  }
}
