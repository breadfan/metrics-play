import scala.util.Properties

organization:= "de.threedimensions"
name := "metrics-play"
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

val playVersion = "2.6.0-RC2"
version in ThisBuild := playVersion + "_" + Properties.envOrElse("TRAVIS_BUILD_NUMBER", "0-SNAPSHOT")

scalaVersion := "2.12.2"

testOptions in Test += Tests.Argument("junitxml", "console")

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "io.dropwizard.metrics" % "metrics-core" % "3.2.2",
  "io.dropwizard.metrics" % "metrics-json" % "3.2.2",
  "io.dropwizard.metrics" % "metrics-jvm" % "3.2.2",
  "io.dropwizard.metrics" % "metrics-logback" % "3.2.2",
  "com.typesafe.play" %% "play" % playVersion % "provided",
  "org.joda" % "joda-convert" % "1.8.1",
  //test
  "com.typesafe.play" %% "play-test" % playVersion % "test",
  "com.typesafe.play" %% "play-specs2" % playVersion % "test",
  "org.specs2" %% "specs2" % "2.4.17" % "test"
)