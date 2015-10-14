package springnz.util

import java.time.format.DateTimeFormatter
import java.time.{ LocalDate, OffsetDateTime, ZoneId, ZonedDateTime }

import org.joda.time.DateTime

object DateTimeUtil {

  val UTCTimeZone: ZoneId = ZoneId.of("UTC")
  val NZTimeZone: ZoneId = ZoneId.of("Pacific/Auckland")

  val isoDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val isoDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")

  def utcOffsetDateTime: OffsetDateTime = OffsetDateTime.now(UTCTimeZone)

  def utcZonedDateTime: ZonedDateTime = ZonedDateTime.now(UTCTimeZone)

  def toIsoDateString(localDate: LocalDate): String = localDate.format(isoDateFormatter)

  def toIsoDateString(zonedDateTime: ZonedDateTime): String = zonedDateTime.format(isoDateFormatter)

  def toIsoDateString(offsetDateTime: OffsetDateTime): String = offsetDateTime.format(isoDateFormatter)

  def toIsoDateString(dateTime: DateTime): String = dateTime.toString("yyyy-MM-dd")

  def toIsoDateTimeString(localDate: LocalDate): String = localDate.format(isoDateTimeFormatter)

  def toIsoDateTimeString(zonedDateTime: ZonedDateTime): String = zonedDateTime.format(isoDateTimeFormatter)

  def toIsoDateTimeString(offsetDateTime: OffsetDateTime): String = offsetDateTime.format(isoDateTimeFormatter)

  def toIsoDateTimeString(dateTime: DateTime): String = dateTime.toString("yyyy-MM-dd hh:mm:ss")
}
