package springnz.util

import play.api.libs.json._

object JsUtil {
  def unwrapFromJs(innerJs: JsValue, level: Int = -1 /* -1 means no limits on recursion depth */): Any = {
    def valOrStr(stringValue: JsValue, value: ⇒ Any) =
      if (level == 0) Json.asciiStringify(stringValue) else value

    innerJs match {
      case JsString(string) ⇒ string
      case JsNumber(number) ⇒ number
      case JsBoolean(bool)  ⇒ bool
      case JsArray(array)   ⇒ valOrStr(innerJs, array map { unwrapFromJs(_, level - 1) })
      case JsObject(inner)  ⇒ valOrStr(innerJs, inner.toMap.mapValues { unwrapFromJs(_, level - 1) })
      case other            ⇒ Json.asciiStringify(other)
    }
  }

}
