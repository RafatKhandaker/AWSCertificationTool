// Name of the project
name := "AWS Certification Tool"

// Project version
version := "19.0.0-R30"

// Version of Scala used by the project
scalaVersion := "2.13.9"

// Add dependency on ScalaFX library
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "19.0.0-R30",
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "org.apache.spark" %% "spark-sql" % "3.2.1",
  "org.apache.tika" % "tika-core" % "2.3.0",
  "org.apache.tika" % "tika-parser-pdf-module" % "2.3.0"
)



scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

mainClass := Some("AppStart.Application")

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true

