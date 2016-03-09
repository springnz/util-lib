package springnz.util

import java.time.format.DateTimeFormatter
import java.time._

import org.joda.time.DateTime

object DateTimeUtil {

  lazy val UTCTimeZone: ZoneId = ZoneId.of("UTC")
  lazy val NZTimeZone: ZoneId = ZoneId.of("Pacific/Auckland")

  def utcOffsetDateTime: OffsetDateTime = OffsetDateTime.now(UTCTimeZone)

  def nzOffsetDateTime: OffsetDateTime = OffsetDateTime.now(NZTimeZone)

  def nzLocalDateTime: LocalDateTime = OffsetDateTime.now(NZTimeZone).toLocalDateTime

  def utcZonedDateTime: ZonedDateTime = ZonedDateTime.now(UTCTimeZone)

  def toIsoDate(localDate: LocalDate): String = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)

  def toIsoDate(zonedDateTime: ZonedDateTime): String = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)

  def toIsoDate(offsetDateTime: OffsetDateTime): String = offsetDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)

  def toIsoDate(dateTime: DateTime): String = dateTime.toString("yyyy-MM-dd")

  def toIsoDateTime(localDate: LocalDate): String = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

  def toIsoDateTime(localDateTime: LocalDateTime): String = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

  def toIsoZonedDateTime(zonedDateTime: ZonedDateTime): String = zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)

  def toIsoOffsetDateTime(offsetDateTime: OffsetDateTime): String = offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

  def toIsoDateTime(dateTime: DateTime): String = dateTime.toString("yyyy-MM-dd'T'hh:mm:ss")

  def isoDateToLocalDate(isoDate: String): LocalDate = LocalDate.parse(isoDate, DateTimeFormatter.ISO_LOCAL_DATE)

  def isoDateTimeToLocalDateTime(isoDateTime: String): LocalDateTime = LocalDateTime.parse(isoDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}
