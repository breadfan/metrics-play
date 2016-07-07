import scala.util.Properties
import bintray.AttrMap
import bintray._

organization:= "de.threedimensions"

name := "metrics-play"

version in ThisBuild := "2.5." + Properties.envOrElse("TRAVIS_BUILD_NUMBER", "0-SNAPSHOT")

scalaVersion := "2.11.8"

testOptions in Test += Tests.Argument("junitxml", "console")

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
    "io.dropwizard.metrics" % "metrics-core" % "3.1.2",
    "io.dropwizard.metrics" % "metrics-json" % "3.1.2",
    "io.dropwizard.metrics" % "metrics-jvm" % "3.1.2",
    "io.dropwizard.metrics" % "metrics-logback" % "3.1.2",
    "com.typesafe.play" %% "play" % "2.5.3" % "provided",
    "org.joda" % "joda-convert" % "1.2",
    //test
    "com.typesafe.play" %% "play-test" % "2.5.4" % "test",
    "com.typesafe.play" %% "play-specs2" % "2.5.4" % "test",
    "org.specs2" %% "specs2" % "2.3.12" % "test"
)

publishMavenStyle := false

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

bintrayPublishSettings

bintray.Keys.repository in bintray.Keys.bintray := "sbt-plugins"

bintray.Keys.bintrayOrganization in bintray.Keys.bintray := None

publishArtifact in Test := false