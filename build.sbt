name := "web-benchmark"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-slf4j"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "ch.qos.logback"      %  "logback-classic" % "1.1.3",
    "org.json4s"          %%  "json4s-native" % "3.3.0.RC6",
    "org.json4s"          %%  "json4s-ext"    % "3.3.0.RC6",
    "com.github.etaty" %% "rediscala" % "1.5.0",
    "org.scalatest"       %%  "scalatest"     % "2.2.1" % "test"
  )
}

parallelExecution in Test := false

import NativePackagerHelper._

enablePlugins(JavaServerAppPackaging)

mainClass in Compile := Some("me.binwang.web_benchmark.Boot")

scalacOptions ++= Seq("-Xmax-classfile-name", "100")

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
      // copy configuration files to config directory
      contentOf("src/main/resources").toMap.mapValues("config/" + _)
}

scriptClasspath := Seq("../config/") ++ scriptClasspath.value

Revolver.settings
