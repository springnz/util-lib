package springnz.util

import java.nio.file.Path
import java.nio.file.{ Paths, Files }
import java.nio.charset.StandardCharsets

import scala.io.Source

object IOUtil {

  def readTextFile(filename: String): String =
    readLines(filename).mkString("\n")

  def readLines(filename: String): Iterator[String] =
    Source.fromFile(filename).getLines()

  def writeTextFile(filename: String, content: String): Path =
    Files.write(Paths.get(filename), content.getBytes(StandardCharsets.UTF_8))

  def createTempFile(): Path = {
    val tmp = System.getProperty("java.io.tmpdir")
    java.nio.file.Files.createTempFile(Paths.get(tmp), "", "")
  }
}
