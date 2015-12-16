package springnz.util

import com.typesafe.scalalogging.Logger

import scala.concurrent.{ExecutionContext, Future}

object Timer extends Logging {
  def timed[A](processName: Option[String] = None)(execution: ⇒ A): A = {
    val start = System.currentTimeMillis()
    val result = execution
    val time = System.currentTimeMillis() - start
    val messageStart = processName.getOrElse(s"'${result.toString}'")
    log.info(s"$messageStart execution time: ${time}ms")
    result
  }

  implicit class LazyFuturePimper[T](f: => Future[T]) {
    def timed(processName: Option[String] = None)(implicit log: Logger, ec: ExecutionContext): Future[T] = {
      val start = System.currentTimeMillis()
      f.zip(Future { start })
        .map {
          case (result, start) ⇒
            val time = System.currentTimeMillis() - start
            val messageStart = processName.getOrElse(s"'${result.toString}'")
            log.info(s"$messageStart execution time: ${time}ms")
            result
        }
    }
  }
}
