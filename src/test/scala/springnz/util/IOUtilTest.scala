package springnz.util

import org.scalatest._

class IOUtilTest extends WordSpec with ShouldMatchers {

  "IOUtil" should {
    "create tmp file, write and read file" in {
      val tmpFile = IOUtil.createTempFile()
      val tmpFileName = tmpFile.toString
      val fileContent = "line1\nline2"

      IOUtil.writeTextFile(tmpFileName, fileContent)
      IOUtil.readTextFile(tmpFileName) shouldBe fileContent
    }
  }
}
