package ylabs.util

import java.time.{ Instant ⇒ JInstant, OffsetDateTime, ZoneId }
import java.util.Date

import org.joda.time.DateTime

object DateTimeUtil {

  val utcZone: ZoneId = ZoneId.of("UTC")

  def utcDateTime: OffsetDateTime = OffsetDateTime.now(utcZone)

  // convenient for scalatest comparisons
  class OrderedOffsetDateTime(offsetDateTime: OffsetDateTime) extends Ordered[OffsetDateTime] {
    def compare(that: OffsetDateTime): Int = offsetDateTime.compareTo(that)
  }

  implicit def orderedOffsetDateTime(offsetDateTime: OffsetDateTime): OrderedOffsetDateTime =
    new OrderedOffsetDateTime(offsetDateTime)

  implicit class OffsetDateTimePimper(offsetDateTime: OffsetDateTime) {
    def toLegacyDate: Date = Date.from(offsetDateTime.toInstant)
    def toJodaTime: DateTime = DateTime.parse(offsetDateTime.toString)
  }

  implicit class LegacyDatePimper(date: Date) {
    def toUtcOffsetDateTime: OffsetDateTime = OffsetDateTime.ofInstant(date.toInstant, DateTimeUtil.utcZone)
  }

  implicit class JodaTimePimper(dateTime: DateTime) {
    def toUtcOffsetDateTime: OffsetDateTime = {
      val jInstant = JInstant.ofEpochMilli(dateTime.toInstant.getMillis)
      OffsetDateTime.ofInstant(jInstant, utcZone)
    }
  }
}
