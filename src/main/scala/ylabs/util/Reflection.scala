package ylabs.util

import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object Reflection {

  def createInstance[T: TypeTag](args: Any*): T = {
    val tt = typeTag[T]
    val ruType = tt.tpe
    val constructorSymbol = ruType.members.filter(m => m.isMethod && m.asMethod.isConstructor).head.asMethod
    val constructorMethod = currentMirror.reflectClass(ruType.typeSymbol.asClass).reflectConstructor(constructorSymbol)
    constructorMethod(args: _*).asInstanceOf[T]
  }
}

