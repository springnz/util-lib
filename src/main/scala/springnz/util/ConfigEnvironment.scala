package springnz.util

import com.typesafe.config.ConfigFactory

import scala.util.Try

object ConfigEnvironment extends Logging {

  val config = {
    val rootConfig = ConfigFactory.load()
    val sharedPrefix: Option[String] = Try { rootConfig.getString("config-shared-prefix") }.toOption
    val environmentPrefix: Option[String] = Try { rootConfig.getString("config-environment-prefix") }.toOption
    val overridePrefix: Option[String] = Try { rootConfig.getString("config-override-prefix") }.toOption

    sharedPrefix.foreach { prefix ⇒
      log.info(s"Using shared config prefix [$prefix]")
    }

    environmentPrefix.foreach { prefix ⇒
      log.info(s"Using environment config prefix [$prefix]")
    }

    val optionalSharedConfig = sharedPrefix.map(rootConfig.getConfig)
    val optionalEnvironmentConfig = environmentPrefix.map(rootConfig.getConfig)
    val overrideEnvironmentConfig = overridePrefix.map(rootConfig.getConfig)

    val configEnv = (optionalEnvironmentConfig, optionalSharedConfig) match {
      case (Some(environmentConfig), Some(sharedConfig)) ⇒
        environmentConfig.withFallback(sharedConfig)
      case (Some(environmentConfig), None) ⇒
        environmentConfig
      case (None, Some(sharedConfig)) ⇒
        sharedConfig
      case _ ⇒
        log.info("Using root config")
        ConfigFactory.load()
    }

    overrideEnvironmentConfig.fold(configEnv)(_.withFallback(configEnv))
  }
}
