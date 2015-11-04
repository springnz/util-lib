package springnz.util

import org.scalatest._
import play.api.libs.json._

class JsUtilTest extends WordSpec with ShouldMatchers {

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
               |]""".stripMargin('|')


  "JsUtil" should {
    "unwrap json with full recursion" in {
      val parsedJson = Json.parse(json)
      val unwrapped = JsUtil.unwrapFromJs(parsedJson)
      unwrapped shouldBe a[Seq[_]]
      val unwrappedSeq = unwrapped.asInstanceOf[Seq[Any]]
      unwrappedSeq.length shouldBe 2
      unwrappedSeq.head shouldBe a[Map[String,_]]
      val first = unwrappedSeq.head.asInstanceOf[Map[String,Any]]
      first.get("friends").get shouldBe a[Seq[_]]
      first.get("age").get shouldBe 32
      first.get("gender").get shouldBe "male"
    }
    "unwrap json with partial recursion" in {
      val parsedJson = Json.parse(json)
      val unwrapped = JsUtil.unwrapFromJs(parsedJson, 2)
      val unwrappedSeq = unwrapped.asInstanceOf[Seq[Any]]
      val first = unwrappedSeq.head.asInstanceOf[Map[String,Any]]
      first.get("friends").get shouldBe """[{"id":0,"name":"Delia Haynes"},{"id":1,"name":"Clark Moore"},{"id":2,"name":"Rosanna Anderson"}]"""
    }
  }
}
