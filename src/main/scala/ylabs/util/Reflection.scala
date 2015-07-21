package ylabs.util

import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object Reflection {

  def createInstance[T: TypeTag](args: Any*): T = {
    val tt = typeTag[T]
    val ruType = tt.tpe
    val firstConstructor = ruType.members.filter(m =>
      m.isMethod && m.asMethod.isConstructor).iterator.toSeq(0).asMethod
    val constructor = currentMirror.reflectClass(ruType.typeSymbol.asClass).reflectConstructor(firstConstructor)
    constructor(args: _*).asInstanceOf[T]
  }
}

