package ylabs.util

import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object Reflection {

  def createInstance[T: TypeTag](args: Any*): T = {
    val ruType = typeTag[T].tpe
    val constructorSymbol = constructor[T]
    val constructorMethod = currentMirror.reflectClass(ruType.typeSymbol.asClass).reflectConstructor(constructorSymbol)
    constructorMethod(args: _*).asInstanceOf[T]
  }

  def createInstanceFromMap[T: TypeTag](argMap: Map[String, _]): T = {
    val arguments = getFieldNames map argMap toSeq
    val instance = createInstance[T](arguments: _*)
    instance
  }

  def getFieldNames[T: TypeTag]: Iterable[String] = constructor.paramLists.head.map(symbol ⇒ symbol.name.toString)

  private def constructor[T: TypeTag](): universe.MethodSymbol =
    typeTag[T].tpe.members
      .find(m ⇒ m.isMethod && m.asMethod.isConstructor) match {
        case Some(m) ⇒ m.asMethod
        case _       ⇒ throw new Exception(s"No constructor method found for type ${typeTag[T].tpe}")
      }

}

