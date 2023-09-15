import FeatureCreation.createFeatureCollection
import Models.{CliArgs, FilePath}
import Parsing.{parseInputCoordinates, readFile}
import ImageGeneration.executeImageGeneration
import cats.effect.unsafe.implicits.global

import java.net.{URI, URLEncoder}

object AppRunner {
  def run(args: CliArgs): Unit = {
    val swapFlag = args.swapFlag
    val downloadFlag = args.downloadFlag
    val browserFlag = args.browserFlag
    val imageSize = args.imageSize
    val inputFile = FilePath(args.inputFile)

    val colors =
      LazyList.continually(List(Red, Blue, Green, Yellow, Purple)).flatten

    val fileSource = readFile(inputFile)
    val inputCoordinates = parseInputCoordinates(fileSource, swapFlag)

    val featureCollection = createFeatureCollection(
      inputCoordinates,
      colors
    )

    val encodedJsonString =
      URLEncoder.encode(featureCollection, "UTF-8")

    val imageWidth = imageSize
    val imageHeight = imageSize
    val apiKey =
      "pk.eyJ1IjoiYXJuYXNiciIsImEiOiJjbG00dXY1MDAybGJrM2RwNnE2dmo1NW01In0.XC_idJ6KnMWc1N-MX-Ry7A"
    val outputPath = FilePath("outputDir/output.png")

    val staticImageUri =
      new URI(
        s"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($encodedJsonString)/auto/${imageWidth}x$imageHeight?access_token=$apiKey"
      )

    executeImageGeneration(
      downloadFlag,
      browserFlag,
      outputPath,
      staticImageUri
    ).unsafeRunSync()
  }
}
