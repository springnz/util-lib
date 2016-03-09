package springnz.util

import java.time.format.DateTimeFormatter
import java.time._

import org.joda.time.DateTime

object DateTimeUtil {

  lazy val UTCTimeZone: ZoneId = ZoneId.of("UTC")
  lazy val NZTimeZone: ZoneId = ZoneId.of("Pacific/Auckland")

  lazy val isoDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  lazy val isoDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  lazy val isoDateTimeWithOffsetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")

  def utcOffsetDateTime: OffsetDateTime = OffsetDateTime.now(UTCTimeZone)

  def nzOffsetDateTime: OffsetDateTime = OffsetDateTime.now(NZTimeZone)

  def nzLocalDateTime: LocalDateTime = OffsetDateTime.now(NZTimeZone).toLocalDateTime

  def utcZonedDateTime: ZonedDateTime = ZonedDateTime.now(UTCTimeZone)

  def toIsoDate(localDate: LocalDate): String = localDate.format(isoDateFormatter)

  def toIsoDate(zonedDateTime: ZonedDateTime): String = zonedDateTime.format(isoDateFormatter)

  def toIsoDate(offsetDateTime: OffsetDateTime): String = offsetDateTime.format(isoDateFormatter)

  def toIsoDate(dateTime: DateTime): String = dateTime.toString("yyyy-MM-dd")

  def toIsoDateTime(localDate: LocalDate): String = localDate.format(isoDateTimeFormatter)

  def toIsoDateTime(localDateTime: LocalDateTime): String = localDateTime.format(isoDateTimeFormatter)

  def toIsoDateTime(zonedDateTime: ZonedDateTime): String = zonedDateTime.format(isoDateTimeFormatter)

  def toIsoDateTime(offsetDateTime: OffsetDateTime): String = offsetDateTime.format(isoDateTimeWithOffsetFormatter)

  def toIsoDateTime(dateTime: DateTime): String = dateTime.toString("yyyy-MM-dd hh:mm:ss")

  def isoDateToLocalDate(isoDate: String): LocalDate = LocalDate.parse(isoDate, isoDateFormatter)

  def isoDateTimeToLocalDateTime(isoDateTime: String): LocalDateTime = LocalDateTime.parse(isoDateTime, isoDateTimeFormatter)
}
