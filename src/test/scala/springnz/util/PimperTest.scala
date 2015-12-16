package springnz.util

import org.scalatest._
import springnz.util.Pimpers._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

class PimperTest extends WordSpec with ShouldMatchers with Logging {
  "FuturePimper" should {
    "followedBy executes second future even if first fails" in {
      val f1 = Future { println("one"); throw new Exception }
      val f2 = Future { println("two"); 2 }

      val f1Thenf2 = f1 followedBy f2

      val result = Await.result(f1Thenf2, 1 second)
      result shouldBe 2
    }
  }

}

