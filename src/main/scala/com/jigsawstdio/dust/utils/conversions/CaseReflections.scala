package com.jigsawstdio.dust.utils.conversions

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}
import org.postgresql.util.PGobject

import scala.reflect._
import scala.reflect.runtime.universe._

object CaseReflections {
  val jsonMapper = new ObjectMapper() with ScalaObjectMapper
  jsonMapper.registerModule(DefaultScalaModule)

  def caseFromMap[T: TypeTag: ClassTag](m: Map[String,_]) = {
    val rm = runtimeMirror(classTag[T].runtimeClass.getClassLoader)
    val classTest = typeOf[T].typeSymbol.asClass
    val classMirror = rm.reflectClass(classTest)
    val constructor = typeOf[T].decl(termNames.CONSTRUCTOR).asMethod
    val constructorMirror = classMirror.reflectConstructor(constructor)

    val constructorArgs = constructor.paramLists.flatten.map( (param: Symbol) => {
      val paramName = param.name.toString
      println(paramName)
      val paramValue = m.get(paramName) match {
        case None => None
        case Some(v) =>
          v match {
            case _:PGobject => Some(v.toString)
            case _ => Some(v)
          }
      }
      if(param.typeSignature <:< typeOf[Option[Any]])
        paramValue
      else {
        if(paramValue.isDefined) paramValue.get
        else throw new IllegalArgumentException(s"Map is missing required parameter named '${paramName}'")
      }
    })

    constructorMirror(constructorArgs:_*).asInstanceOf[T]
  }

}
