import com.typesafe.sbt.packager.docker.DockerPermissionStrategy
enablePlugins(DockerPlugin, JavaAppPackaging, GitVersioning)

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

name := "geojson-formatter"
organization := "ch.epfl.scala"
version := git.gitHeadCommit.value.getOrElse("0.1").take(5)

Docker / packageName := packageName.value
Docker / version := version.value
dockerBaseImage := "openjdk:17"
dockerExposedPorts := Seq(9000, 9443)
dockerExposedVolumes := Seq("/opt/docker/logs")
Docker / defaultLinuxInstallLocation := "/opt/docker"
Docker / daemonUserUid := None
Docker / daemonUser := "daemon"

dockerRepository := Some("arnasbr")
dockerUpdateLatest := true
dockerPermissionStrategy := DockerPermissionStrategy.MultiStage
Docker / dockerGroupLayers := PartialFunction.empty

lazy val root = (project in file("."))
  .settings(
    name := "geojsonFormatter"
  )

val circeVersion = "0.14.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.4"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.5.0"
libraryDependencies += "io.circe" %% "circe-core" % circeVersion
libraryDependencies += "io.circe" %% "circe-parser" % circeVersion
libraryDependencies += "io.circe" %% "circe-generic" % circeVersion
