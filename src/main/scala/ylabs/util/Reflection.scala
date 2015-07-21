package ylabs.util

import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object Reflection {

  def createInstance[T: TypeTag](args: Any*): T = {
    val ruType = typeTag[T].tpe
    val constructorSymbol = constructor[T]
    val p: List[universe.Symbol] = constructorSymbol.paramLists(0)
    val constructorMethod = currentMirror.reflectClass(ruType.typeSymbol.asClass).reflectConstructor(constructorSymbol)
    constructorMethod(args: _*).asInstanceOf[T]
  }

  def createInstanceFromMap[T: TypeTag](argMap: Map[String, _]): T = {println(typeOf[T].members)
    val arguments = getFieldNames map argMap toSeq
    val instance = createInstance(arguments: _*)
    instance
  }

  def getFieldNames[T: TypeTag]: Iterable[String] = constructor.paramLists(0).map (a => a.name.toString)

  private def constructor[T: TypeTag](): universe.MethodSymbol = typeTag[T].tpe.members
    .filter { m => m.isMethod && m.asMethod.isConstructor }
    .head.asMethod

}

