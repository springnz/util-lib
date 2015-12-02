package springnz.util

import org.scalatest._

class BuilderOpsTest extends WordSpec with ShouldMatchers {
  import BuilderOps._
  "BuilderOps" should {
    "be applied for Somes" in {
      val list = List("C", "B", "A")
      val newList = list.setOptional[String](Some("D"), (list, value) => value :: list)
      newList shouldBe List("D", "C", "B", "A")
    }

    "leave the instance unchanged for Nones" in {
      val list = List[String]("C", "B", "A")
      val newList = list.setOptional[String](None, (list, value) => value :: list)
      newList should be theSameInstanceAs list
    }

    "be applied for Non emptys" in {
      val list = List("C", "B", "A")
      val newList = list.setIfNonEmpty("D", (list, value) => value :: list)
      newList shouldBe List("D", "C", "B", "A")
    }

    "leave the instance unchanged for non-empties" in {
      val list = List[String]("C", "B", "A")
      val newList = list.setIfNonEmpty("", (list, value) => value :: list)
      newList should be theSameInstanceAs list
    }
  }
}
