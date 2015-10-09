package springnz.util

import org.scalatest._

class HashUtilTest extends WordSpec with ShouldMatchers {

  "HashUtil" should {
    "compute md5" in {
      HashUtil.md5("some text") shouldBe "552e21cd4cd9918678e3c1a0df491bc3"
    }
    "compute sha1" in {
      HashUtil.sha1("some text") shouldBe "37aa63c77398d954473262e1a0057c1e632eda77"
    }
  }
}
