package springnz.util

import org.json4s._
import org.json4s.jackson.JsonMethods._

object Json4sUtil {

  implicit class JValueOps(jValue: JValue) {
    implicit val formats = DefaultFormats

    def noEmptyObjects: JValue =
      jValue remove {
        case jObj: JObject if jObj.children.isEmpty ⇒ true
        case _                                      ⇒ false
      }

    def getDecimal: Option[BigDecimal] =
      jValue match {
        case JDecimal(value) ⇒ Some(value)
        case _               ⇒ None
      }

    def getBigInt: Option[BigInt] =
      jValue match {
        case JInt(value) ⇒ Some(value)
        case _           ⇒ None
      }

    def getLong: Option[Long] =
      jValue match {
        case JInt(value) ⇒ Some(value.toLong)
        case _           ⇒ None
      }

    def getInt: Option[Int] =
      jValue match {
        case JInt(value) ⇒ Some(value.toInt)
        case _           ⇒ None
      }

    def getString: Option[String] =
      jValue match {
        case JString(value) ⇒ Some(value)
        case _              ⇒ None
      }

    def getBoolean: Option[Boolean] =
      jValue match {
        case JBool(value) ⇒ Some(value)
        case _            ⇒ None
      }

    def getDouble: Option[Double] =
      jValue match {
        case JDouble(value) ⇒ Some(value)
        case _              ⇒ None
      }

    // not working until we upgrade to latest json4s
    //    def toMap: Map[String, Any] = {
    //      jValue.extract[Map[String, Any]]
    //    }

    // depth = -1 means no limits on recursion depth
    def toMap(depth: Int = -1): Any = {

      def toMapRecursive(innerJs: JValue, level: Int = -1): Any = {
        def valOrStr(stringValue: JValue, value: ⇒ Any) =
          if (level == 0) compact(stringValue) else value

        innerJs match {
          case JString(string) ⇒ string
          case JInt(number)    ⇒ number
          case JBool(bool)     ⇒ bool
          case JArray(array)   ⇒ valOrStr(innerJs, array map { toMapRecursive(_, level - 1) })
          case JObject(inner)  ⇒ valOrStr(innerJs, inner.toMap.mapValues { toMapRecursive(_, level - 1) })
          case other           ⇒ compact(other)
        }
      }

      toMapRecursive(jValue, depth)
    }
  }

  // Takes a list of json entities, and checks if each field has the same value across all entities.
  // Returns the fields which have different values.
  def diff(jsonList: Seq[JValue]): Set[String] = {
    val numElements = jsonList.size
    val jsonMaps = jsonList.map(_.toMap().asInstanceOf[Map[String, Any]])
    val fieldNames = jsonMaps.flatMap(_.keys).toSet
    for {
      fieldName ← fieldNames
      values = jsonMaps.flatMap(_.get(fieldName))
      if values.size != numElements || (values.size == numElements && values.distinct.size > 1)
    } yield {
      fieldName
    }
  }
}
