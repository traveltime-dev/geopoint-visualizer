ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "geopoint-visualizer"
  )

val circeVersion = "0.14.5"
libraryDependencies += "io.circe" %% "circe-core" % circeVersion
libraryDependencies += "io.circe" %% "circe-parser" % circeVersion
libraryDependencies += "io.circe" %% "circe-generic" % circeVersion
libraryDependencies += "com.monovore" %% "decline" % "2.4.1"
libraryDependencies += "com.softwaremill.sttp.client4" %% "core" % "4.0.0-M4"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.1"
