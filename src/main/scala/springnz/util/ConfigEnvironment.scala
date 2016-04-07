package springnz.util

import com.typesafe.config.ConfigFactory

object ConfigEnvironment extends Logging {

  val config = {
    val rootConfig = ConfigFactory.load()
    val deploymentEnvironment = rootConfig.getString("deployment-environment")
    log.info(s"Using $deploymentEnvironment config")
    rootConfig.getConfig(deploymentEnvironment)
  }

}
