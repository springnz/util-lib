package springnz.util

import com.typesafe.config.{ Config, ConfigFactory }

import scala.util.Try

object ConfigEnvironment extends Logging {

  val config: Config = {
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

    def getConfig(configString: Option[String]) = Try { configString.map(rootConfig.getConfig) }.toOption.flatten

    val optionalSharedConfig = getConfig(sharedPrefix)
    val optionalEnvironmentConfig = getConfig(environmentPrefix)
    val overrideEnvironmentConfig = getConfig(overridePrefix)

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

    overrideEnvironmentConfig.map(_.withFallback(configEnv)).getOrElse(configEnv)
  }
}
