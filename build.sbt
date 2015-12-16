name := "util-lib"
organization := "springnz"
scalaVersion := "2.11.7"

resolvers ++= Seq(
  Resolver.mavenLocal
)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "joda-time" % "joda-time" % "2.8.1",
  "com.typesafe.play" %% "play-json" % "2.4.2" exclude ("org.slf4j", "slf4j-log4j12"),
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "org.scalatest" %% "scalatest" % "2.2.4" % Test,
  "ch.qos.logback" % "logback-classic" % "1.1.3" % Test,
  "org.mockito" % "mockito-core" % "1.10.19" % Test
)

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

val repo = "https://nexus.prod.corp/content"
publishTo <<= version { (v: String) ⇒
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at s"$repo/repositories/snapshots")
  else Some("releases" at s"$repo/repositories/releases")
}

releaseSettings
ReleaseKeys.versionBump := sbtrelease.Version.Bump.Minor
ReleaseKeys.tagName := s"${name.value}-v${version.value}"

