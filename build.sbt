import sbt._

name := "chat-bot-service"
organization := "com.sean"
scalaVersion := "2.12.4"
version := "0.1"

libraryDependencies ++= {
  val AkkaVersion = "2.4.18"
  val AkkaHttpVersion = "10.0.6"
  Seq(
    "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "joda-time" % "joda-time" % "2.9.9",
    "com.typesafe.akka" %% "akka-stream" % "2.5.9",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.0-RC2"
  )
}


