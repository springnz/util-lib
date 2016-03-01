package springnz.util

import org.scalatest._

class IOUtilTest extends WordSpec with ShouldMatchers {

  "IOUtil" should {
    "create tmp file, write and read file" in {
      val tmpFile = IOUtil.createTempFile()
      val tmpFileName = tmpFile.getFileName.toString
      val fileContent = Seq("line1", "line2")

      IOUtil.writeTextFile(tmpFileName, fileContent.mkString("\n"))
      IOUtil.readTextFile(tmpFileName) shouldBe fileContent
    }
  }
}
