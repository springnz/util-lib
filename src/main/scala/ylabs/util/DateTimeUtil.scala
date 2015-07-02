package ylabs.util

import java.time.{OffsetDateTime, ZoneId}

object DateTimeUtil {

  val utcZone: ZoneId = ZoneId.of("UTC")

  def utcDateTime: OffsetDateTime = OffsetDateTime.now(utcZone)

}
