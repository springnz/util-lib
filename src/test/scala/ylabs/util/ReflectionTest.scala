package ylabs.util

import org.scalatest._

case class Person(name: String, age: Int, email: String)

class ReflectionTest extends WordSpec with ShouldMatchers {
  "Reflection" should {
    "constructs a Person object" in {
      val person = Reflection.createInstance[Person]("Bob", 42, "bob@spring.nz")
      person shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "works for a sequence" in {
      val args = Seq("Bob", 42, "bob@spring.nz")
      val person = Reflection.createInstance[Person](args: _*)
      person shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "works for a map" in {
      val argMap = Map("name" -> "Bob", "age" -> 42, "email" -> "bob@spring.nz")
      val person = Reflection.createInstanceFromMap[Person](argMap)
      person shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "works for a map with ClassTag" in {
      val argMap = Map("name" -> "Bob", "age" -> 42, "email" -> "bob@spring.nz")
      val person = Reflection.createInstanceFromMap2[Person](argMap)
      person shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "works for a map with strings" in {
      val argMap = Map("name" -> "Bob", "age" -> "42", "email" -> "bob@spring.nz")
      val person = Reflection.createInstanceFromMap[Person](argMap)
      person shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "get the constructor arguments / fields of a case class" in {
      val fields = Reflection.getFieldDetails[Person] map (_._1)
      fields.toList shouldBe List("name", "age", "email")
    }
  }
}
