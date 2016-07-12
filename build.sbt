import scala.util.Properties
import bintray._

organization:= "de.threedimensions"
name := "metrics-play"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

version in ThisBuild := "2.5." + Properties.envOrElse("TRAVIS_BUILD_NUMBER", "0-SNAPSHOT")

scalaVersion := "2.11.8"

val playVersion = "2.5.4"

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
    "com.typesafe.play" %% "play-test" % playVersion % "test",
    "com.typesafe.play" %% "play-specs2" % playVersion % "test",
    "org.specs2" %% "specs2" % "2.3.12" % "test"
)

publishMavenStyle := false