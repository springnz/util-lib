package ylabs.util

import java.time.OffsetDateTime
import java.util.Date

import com.typesafe.scalalogging.Logger

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

    def withFinally[T](block: ⇒ T): Try[A] =
      t match {
        case Success(result: A) ⇒
          block
          Success(result)
        case Failure(e) ⇒
          block
          Failure(e)
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

}
