package springnz.util

import java.security.MessageDigest

object HashUtil {

  def bytesToHex(bytes: Array[Byte]): String = bytes.map("%02x".format(_)).mkString

  def md5(text: String): String = {
    val md5Digest = MessageDigest.getInstance("MD5")
    bytesToHex(md5Digest.digest(text.getBytes))
  }

  def sha1(text: String): String = {
    val sha1Digest = MessageDigest.getInstance("SHA1")
    bytesToHex(sha1Digest.digest(text.getBytes))
  }

}
