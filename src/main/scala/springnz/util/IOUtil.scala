package springnz.util

import scala.io.Source

object IOUtil {

  def readTextFile(filename: String): String =
    Source.fromFile(filename).getLines().mkString("\n")

}
