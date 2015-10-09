package springnz.util

import scala.reflect.runtime._
import scala.reflect.runtime.universe._
import scala.util.Try
import Pimpers._

case class ClassReader[T <: AnyRef : TypeTag]() extends Logging {

  lazy val constructor: universe.MethodSymbol = createConstructor
  lazy val parameters: List[universe.Symbol] = constructor.paramLists.head

  def createInstance(args: Any*): Try[T] = {
    Try[T] {
      if (args.length != parameters.length)
        throw new IllegalArgumentException(s"Too many arguments: - argument list: $args")
      val ruType: universe.Type = typeTag[T].tpe

      val constructorMethod: universe.MethodMirror =
        currentMirror.reflectClass(ruType.typeSymbol.asClass).reflectConstructor(constructor)

      constructorMethod(args: _*).asInstanceOf[T]
    }.withErrorLog(s"Error creating instance of type ${typeTag[T].tpe.toString}")
  }

  def createInstanceFromMap(argMap: Map[String, _]): Try[T] = {
    val argTry: Try[Seq[Any]] =
      for {
        fieldDetails ← getFieldDetails
      } yield fieldDetails.map {
        case (name, tpe) ⇒ argMap(name)
      } toSeq

    argTry.flatMap(args ⇒ createInstance(args: _*))
  }

  def getFieldDetails: Try[Iterable[(String, Type)]] = Try {
    parameters.map {
      (symbol: universe.Symbol) ⇒ (symbol.name.toString, symbol.typeSignature)
    }
  }

  private def createConstructor(): universe.MethodSymbol = {
    val members: universe.MemberScope = typeTag[T].tpe.members
    members.find { m ⇒ m.isMethod && m.asMethod.isPrimaryConstructor } match {
      case Some(m) ⇒ m.asMethod
      case _       ⇒ throw new Exception(s"No suitable constructor method found for type ${typeTag[T].tpe.toString}")
    }
  }
}
