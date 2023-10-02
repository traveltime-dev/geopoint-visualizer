import com.typesafe.sbt.packager.docker.DockerPermissionStrategy

enablePlugins(DockerPlugin, JavaAppPackaging, GitVersioning)

name := "geopoint-visualizer"
organization := "igeolise"
version := git.gitHeadCommit.value.getOrElse("0.1").take(5)

Docker / packageName := packageName.value
Docker / version := version.value
dockerBaseImage := "openjdk:11"
dockerExposedVolumes := Seq(
  "/app/inputDir",
  "/app/outputDir"
)
Docker / defaultLinuxInstallLocation := "/app"
dockerRepository := Some("arnasbr")
dockerUpdateLatest := true
dockerPermissionStrategy := DockerPermissionStrategy.MultiStage

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "geopoint-visualizer"
  )

val circeVersion = "0.14.5"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "com.monovore" %% "decline" % "2.4.1",
  "com.softwaremill.sttp.client3" %% "core" % "3.9.0",
  "com.softwaremill.sttp.client3" %% "cats" % "3.9.0",
  "org.typelevel" %% "cats-effect" % "3.5.1",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test
)
