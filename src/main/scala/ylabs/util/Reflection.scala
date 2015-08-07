package ylabs.util

import scala.reflect.ClassTag
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object Reflection {
  case class ParseOp[T](op: String ⇒ T)
  implicit val popDouble = ParseOp[Double](_.toDouble)
  implicit val popInt = ParseOp[Int](_.toInt)

  def createInstanceFromMap2[T: ClassTag](argMap: Map[String, Any]): T = {
    val classTag = implicitly[ClassTag[T]]
    val constructors = classTag.runtimeClass.getConstructors
    val consHead = constructors.head
    val paramTypes = consHead.getAnnotatedParameterTypes
    val arguments = consHead.getParameters map {
      paramType ⇒
        val name = paramType.getName
        val value = argMap(name)
        value
    }
    val inst = consHead.newInstance(arguments).asInstanceOf[T]
    inst
  }

  def createInstance[T: TypeTag](args: Any*): T = {
    val ruType: universe.Type = typeTag[T].tpe
    val constructorSymbol: universe.MethodSymbol = constructor[T]
    val constructorMethod = currentMirror.reflectClass(ruType.typeSymbol.asClass).reflectConstructor(constructorSymbol)
    constructorMethod(args: _*).asInstanceOf[T]
  }

  def createInstanceFromMap[T: TypeTag](argMap: Map[String, _]): T = {
    val arguments = getFieldDetails map {
      case (name, tpe) ⇒ {
        argMap(name)
      }
    } toSeq
    implicit def strToInt(x: String) = x.toInt
    val instance = createInstance[T](arguments: _*)
    instance
  }

  def getFieldDetails[T: TypeTag]: Iterable[(String, Type)] = {
    val cons: universe.MethodSymbol = constructor[T]()
    cons.paramLists.head.map(
      (symbol: universe.Symbol) ⇒
        (symbol.name.toString, symbol.typeSignature))
  }

  private def constructor[T: TypeTag](): universe.MethodSymbol =
    typeTag[T].tpe.members
      .find { m ⇒ m.isMethod && m.asMethod.isConstructor } match {
        case Some(m) ⇒ m.asMethod
        case _       ⇒ throw new Exception(s"No constructor method found for type ${typeTag[T].tpe.toString}")
      }

}

