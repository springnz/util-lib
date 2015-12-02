package springnz.util

import org.scalatest._

class ResourceUtilTest extends WordSpec with ShouldMatchers {

  "ResourceUtil" should {
    "get project dir" in {
      val projectDir = ResourceUtil.getProjectDir()
      projectDir.endsWith("/util-lib/") shouldBe true
    }
    "load a resource" in {
      val resource = ResourceUtil.loadResourceAsString("src/test/resources/foo/foo.txt")
      resource shouldBe "foo"
    }
  }
}
