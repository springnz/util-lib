package springnz.util

import org.scalatest._
import springnz.util.Pimpers._

class MapPimperTest extends WordSpec with ShouldMatchers {

  "MapPimper" should {
    "remove nones" in {
      val optionMap = Map[String, Option[String]]("foo" -> Some("Foo"), "none" -> None, "bar" -> Some("Bar"))
      optionMap.removeNones() shouldBe Map("foo" -> "Foo", "bar" -> "Bar")
    }

    "Map with remove nones" in {
      val map = Map("foo" -> "Foo", "bar" -> "Bar")
      map.mapValuesRemoveNones(s ⇒ s.find(_ == 'a')) shouldBe Map("bar" -> 'a')
    }

    "Find the first in a list" in {
      val map = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)
      map.getFirst("b", "c", "a") shouldBe Some(2)
      map.getFirst("c", "b", "a") shouldBe Some(3)
    }
  }
}



