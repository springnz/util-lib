package springnz.util

import org.scalatest._

case class Person(name: String, age: Int, email: String)

class ClassReaderTest extends WordSpec with ShouldMatchers {
  "ClassReader" should {
    "construct a Person object" in {
      val person = ClassReader[Person].createInstance("Bob", 42, "bob@spring.nz")
      person.get shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "work for a sequence" in {
      val args = Seq("Bob", 42, "bob@spring.nz")
      val person = ClassReader[Person].createInstance(args: _*)
      person.get shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "work for a map" in {
      val argMap = Map("name" -> "Bob", "age" -> 42, "email" -> "bob@spring.nz")
      val person = ClassReader[Person].createInstanceFromMap(argMap)
      person.get shouldBe Person("Bob", 42, "bob@spring.nz")
    }
  }
  it should {
    "get the constructor arguments / fields of a case class" in {
      val fields = ClassReader[Person].getFieldDetails.get map (_._1)
      fields.toList shouldBe List("name", "age", "email")
    }
  }
  it should {
    "throw an exception if the wrong key is used" in {
      val argMap = Map("namewrong" -> "Bob", "age" -> 42, "email" -> "bob@spring.nz")
      intercept[NoSuchElementException] {
        ClassReader[Person].createInstanceFromMap(argMap).get
      }
    }
  }
  it should {
"throw an exception if called with wrong types" in {
  intercept[IllegalArgumentException] {
        val person = ClassReader[Person].createInstance("Bob", "42", "bob@spring.nz").get
      }
    }
  }
  it should {
    "throw an exception if called with too few parameters" in {
      intercept[IllegalArgumentException] {
        val person = ClassReader[Person].createInstance("Bob", 42).get
      }
    }
  }
  it should {
    "throw an exception if called with too many parameters" in {
      intercept[IllegalArgumentException] {
        val person = ClassReader[Person].createInstance("Bob", 42, "bob@spring.nz", "TooMany").get
      }
    }
  }
}
