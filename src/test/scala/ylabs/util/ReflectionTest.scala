package ylabs.util

import org.scalatest._

case class Person(name: String, age: Int)

class ReflectionTest extends WordSpec with ShouldMatchers {
  "Reflection" should {
    "constructs a Person object" in {
      val person = Reflection.createInstance[Person]("Bob", 42)
      person shouldBe Person("Bob", 42)
    }
  }
  it should {
    "works for a sequence" in {
      val args = Seq("Bob", 42)
      val person = Reflection.createInstance[Person](args: _*)
      person shouldBe Person("Bob", 42)
    }
  }
}
