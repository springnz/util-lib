package springnz.util

import org.scalatest._
import springnz.util.Pimpers._

import scala.util.{Failure, Success, Try}

class TryListPimperTest extends WordSpec with ShouldMatchers {

  "TryListPimper" should {
    "succeed if all succeed" in {
      List[Try[Int]](Success(1), Success(2), Success(3)).sequence() shouldBe Success(List(1,2,3))
      List[Try[String]](Success("1"), Success("2"), Success("3")).sequence() shouldBe Success(List("1","2","3"))
    }

    "Fail with first failure" in {
      val throwable2 = new Throwable("2")
      val throwable3 = new Throwable("3")

      List[Try[Int]](Success(1), Failure(throwable2), Failure(throwable3)).sequence() shouldBe Failure(throwable2)
    }
  }
}
