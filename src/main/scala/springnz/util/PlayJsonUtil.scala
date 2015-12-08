package springnz.util

import play.api.libs.json._

object PlayJsonUtil {

  implicit class JsValueOps(jsValue: JsValue) {

    // depth = -1 means no limits on recursion depth
    def toMap(depth: Int = -1): Any = {

      def toMapRecursive(innerJs: JsValue, level: Int): Any = {
        def valOrStr(stringValue: JsValue, value: ⇒ Any) =
          if (level == 0) Json.asciiStringify(stringValue) else value

        innerJs match {
          case JsString(string) ⇒ string
          case JsNumber(number) ⇒ number
          case JsBoolean(bool)  ⇒ bool
          case JsArray(array) ⇒ valOrStr(innerJs, array map {
            toMapRecursive(_, level - 1)
          })
          case JsObject(inner) ⇒ valOrStr(innerJs, inner.toMap.mapValues {
            toMapRecursive(_, level - 1)
          })
          case other ⇒ Json.asciiStringify(other)
        }
      }

      toMapRecursive(jsValue, depth)
    }
  }

}
