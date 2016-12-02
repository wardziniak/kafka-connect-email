name := "kafka-connect-email"

version := "1.0"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "connect-api" % "0.10.1.0" % "provided",
  "org.apache.kafka" % "connect-file" % "0.10.1.0",
  "org.apache.kafka" % "connect-runtime" % "0.10.1.0",
  "org.apache.kafka" % "connect-json" % "0.10.1.0",
  "io.spray" % "spray-json_2.11" % "1.3.2",
  "org.apache.commons" % "commons-email" % "1.4",
  "com.google.code.gson" % "gson" % "2.6.2"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}