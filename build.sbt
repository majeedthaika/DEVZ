import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

val ScalatraVersion = "2.5.0"

ScalatraPlugin.scalatraSettings

scalateSettings

organization := "io.muic.dev"

name := "DEVZ Project"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.xerial" % "sqlite-jdbc" % "3.8.7",
  "com.typesafe.slick" %% "slick" % "3.1.1"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.12",
  "com.typesafe.akka" %% "akka-agent" % "2.4.12",
  "com.typesafe.akka" %% "akka-camel" % "2.4.12",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.12",
  "com.typesafe.akka" %% "akka-cluster-metrics" % "2.4.12",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.12",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.12",
  "com.typesafe.akka" %% "akka-contrib" % "2.4.12",
  "com.typesafe.akka" %% "akka-multi-node-testkit" % "2.4.12",
  "com.typesafe.akka" %% "akka-osgi" % "2.4.12",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.12",
  "com.typesafe.akka" %% "akka-persistence-tck" % "2.4.12",
  "com.typesafe.akka" %% "akka-remote" % "2.4.12",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.12",
  "com.typesafe.akka" %% "akka-stream" % "2.4.12",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.12",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.12",
  "com.typesafe.akka" %% "akka-distributed-data-experimental" % "2.4.12",
  "com.typesafe.akka" %% "akka-typed-experimental" % "2.4.12",
  "com.typesafe.akka" %% "akka-persistence-query-experimental" % "2.4.12"
)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

scalateTemplateConfig in Compile := {
  val base = (sourceDirectory in Compile).value
  Seq(
    TemplateConfig(
      base / "webapp" / "WEB-INF" / "templates",
      Seq.empty,  /* default imports should be added here */
      Seq(
        Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
      ),  /* add extra bindings here */
      Some("templates")
    )
  )
}

enablePlugins(JettyPlugin)
