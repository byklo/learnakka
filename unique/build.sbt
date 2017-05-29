name := "unique"

version := "0.1"

scalaVersion := "2.12.1"

lazy val akkaVersion = "2.4.17"
lazy val akkaHttpVersion = "10.0.6"

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % akkaVersion,
	"com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
	"org.bouncycastle" % "bcprov-jdk15on" % "1.57"
)

