package springnz.util

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.scalatest._

import scala.collection.mutable.ListBuffer

class Json4sUtilTest extends WordSpec with ShouldMatchers {
  import Json4sUtil._

  // Generated using http://www.json-generator.com/
  val json = """[
               |  {
               |    "_id": "563a8f1e97015d6b161ecc8c",
               |    "index": 0,
               |    "guid": "be384a99-992d-4ea3-92e5-a4b3023be884",
               |    "isActive": true,
               |    "balance": "$2,076.12",
               |    "picture": "http://placehold.it/32x32",
               |    "age": 32,
               |    "eyeColor": "brown",
               |    "name": "Gillespie Dominguez",
               |    "gender": "male",
               |    "company": "PLASMOS",
               |    "email": "gillespiedominguez@plasmos.com",
               |    "phone": "+1 (894) 466-3715",
               |    "address": "418 Dunne Court, Eureka, North Dakota, 6332",
               |    "friends": [
               |      {
               |        "id": 0,
               |        "name": "Delia Haynes"
               |      },
               |      {
               |        "id": 1,
               |        "name": "Clark Moore"
               |      },
               |      {
               |        "id": 2,
               |        "name": "Rosanna Anderson"
               |      }
               |    ]
               |  },
               |  {
               |    "_id": "563a8f1e5676fcf7aea4d0da",
               |    "index": 1,
               |    "guid": "fe72a759-d27c-4452-b67a-ca193c238e4e",
               |    "isActive": true,
               |    "balance": "$3,763.63",
               |    "picture": "http://placehold.it/32x32",
               |    "age": 31,
               |    "eyeColor": "brown",
               |    "name": "Griffith Chambers",
               |    "gender": "male",
               |    "company": "STELAECOR",
               |    "email": "griffithchambers@stelaecor.com",
               |    "phone": "+1 (952) 509-3269",
               |    "address": "252 Alice Court, Florence, Washington, 2230",
               |    "friends": [
               |      {
               |        "id": 0,
               |        "name": "Brandy Sexton"
               |      },
               |      {
               |        "id": 1,
               |        "name": "Emma Gibson"
               |      },
               |      {
               |        "id": 2,
               |        "name": "Wooten Farley"
               |      }
               |    ]
               |  }
               |]""".stripMargin

  "JsUtil" should {

    "unwrap json to two levels" in {
      val json =
        """|
          |[
          |  {
          |    "id": 1,
          |    "name": "Bob",
          |    "friends": [
          |      {
          |        "id": 1,
          |        "name": "Jim"
          |      },
          |      {
          |        "id": 2,
          |        "name": "Rosanna"
          |      }
          |    ]
          |  },
          |  {
          |    "id": 2,
          |    "name": "Jim",
          |    "friends": [
          |      {
          |        "id": 1,
          |        "name": "Bob"
          |      }
          |    ]
          |  }
          |]
        """.stripMargin
      val parsedJson = parse(json)
      val unwrapped = parsedJson.toMap(2)
      val expected = ListBuffer(
        Map("id" -> 1, "name" -> "Bob", "friends" -> """[{"id":1,"name":"Jim"},{"id":2,"name":"Rosanna"}]"""),
        Map("id" -> 2, "name" -> "Jim", "friends" -> """[{"id":1,"name":"Bob"}]""")
      )
      unwrapped shouldBe expected
    }

    "unwrap json with full recursion" in {
      val parsedJson = parse(json)
      val unwrapped = parsedJson.toMap()
      unwrapped shouldBe a[Seq[_]]
      val unwrappedSeq = unwrapped.asInstanceOf[Seq[Any]]
      unwrappedSeq.length shouldBe 2
      unwrappedSeq.head shouldBe a[Map[String, _]]
      val first = unwrappedSeq.head.asInstanceOf[Map[String, Any]]
      first.get("friends").get shouldBe a[Seq[_]]
      first.get("age").get shouldBe 32
      first.get("gender").get shouldBe "male"
    }

    "unwrap json with partial recursion" in {
      val parsedJson = parse(json)
      val unwrapped = parsedJson.toMap(2)
      val unwrappedSeq = unwrapped.asInstanceOf[Seq[Any]]
      val first = unwrappedSeq.head.asInstanceOf[Map[String, Any]]
      first.get("friends").get shouldBe """[{"id":0,"name":"Delia Haynes"},{"id":1,"name":"Clark Moore"},{"id":2,"name":"Rosanna Anderson"}]"""
    }

    "extract values" in {
      (parse(""" { "key": "value" } """) \ "key").getString shouldBe Some("value")
      (parse(""" { "key": "value" } """) \ "key").getInt shouldBe None
      (parse(""" { "key": true } """) \ "key").getBoolean shouldBe Some(true)
      (parse(""" { "key": 12345 } """) \ "key").getInt shouldBe Some(12345)
      (parse(""" { "key": 12345 } """) \ "key").getLong shouldBe Some(12345L)
      (parse(""" { "key": 123.45 } """) \ "key").getDouble shouldBe Some(123.45)
      (parse(""" { "key": 12345 } """) \ "key").getBigInt shouldBe Some(BigInt(12345))
      (parse(""" { "key": 123.45 } """, useBigDecimalForDouble = true) \ "key").getDecimal shouldBe Some(BigDecimal(123.45))
    }

    "convert to Map[String, Any]" in {
      val map = parse(""" { "field": 1 } """).toMapStringAny
      map("field") shouldBe 1

      intercept[IllegalArgumentException] {
        parse(""" [ {} ] """).toMapStringAny
      }
    }
  }
}
