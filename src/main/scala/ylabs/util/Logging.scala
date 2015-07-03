package ylabs.util

import com.typesafe.scalalogging.LazyLogging

trait Logging extends LazyLogging {
  implicit lazy val log = logger
}
