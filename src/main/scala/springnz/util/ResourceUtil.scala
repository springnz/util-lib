package springnz.util

import java.io.{ File ⇒ JFile }

import scala.io.Source

object ResourceUtil {

  def getProjectDir(projectName: Option[String] = None): String = {
    val userDir = System.getProperty("user.dir")
    projectName match {
      case Some(name) if !userDir.endsWith(name) ⇒
        userDir + "/" + name + "/"
      case _ ⇒
        userDir + "/"
    }
  }

  def loadResourceAsJFile(path: String, projectName: Option[String] = None): JFile = {
    val fullPath = getProjectDir(projectName) + path
    new JFile(fullPath)
  }

  def loadResourceAsString(path: String, projectName: Option[String] = None): String = {
    val resourceFile = loadResourceAsJFile(path, projectName)
    Source.fromFile(resourceFile).getLines().mkString("\n")
  }

}
