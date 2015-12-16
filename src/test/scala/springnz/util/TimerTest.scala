package springnz.util

import org.scalatest._
import springnz.util.Timer._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

class TimerTest extends WordSpec with ShouldMatchers with Logging {
  "LazyFutureTimer" should {
    "produce the correct answer when timing" in {
      def f = Future {
        blocking { Thread.sleep(1000) }
        42
      }.timed(Some("Sleep for 1000ms"))
      Await.result(f, 2 seconds) shouldBe 42
    }
  }

  "ExecutionTimer" should {
    "produce the correct answer when timing" in {
      def result = timed[Int](Some("Sleep for 1000ms")) {
        blocking { Thread.sleep(1000) }
        42
      }
      result shouldBe 42
    }
  }

  // Unfortunately unable to Mock a Final class (Logger) to assert the logging interactions
}
