ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "geojsonFormatter"
  )

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.4"
libraryDependencies += "com.monovore" %% "decline" % "2.4.1"
