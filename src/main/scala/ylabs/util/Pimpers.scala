package ylabs.util

import java.time.{ Instant ⇒ JInstant, OffsetDateTime }
import java.util.Date

import com.typesafe.scalalogging.Logger
import org.joda.time.DateTime

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }

object Pimpers {

  implicit class TryPimper[A](t: Try[A]) {
    def withErrorLog(msg: String)(implicit log: Logger): Try[A] =
      t.recoverWith {
        case e ⇒
          log.error(msg, e)
          Failure(e)
      }

    def withFinally[T](block: ⇒ T): Try[A] = {
      block
      t
    }
  }

  implicit class FuturePimper[T](f: Future[T]) {
    def withErrorLog(msg: String)(implicit log: Logger, ec: ExecutionContext): Future[T] = {
      f.onFailure {
        case e ⇒ log.error(msg, e)
      }
      f
    }
  }

  implicit class OptionPimper[T](o: Option[T]) {
    def withErrorLog(msg: String)(implicit log: Logger): Option[T] =
      o.orElse {
        log.error(msg)
        None
      }
  }

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
      OffsetDateTime.ofInstant(jInstant, DateTimeUtil.utcZone)
    }
  }
}
