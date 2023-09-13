import FeatureCreation.createFeatureCollection
import Models.{CliArgs, OutputFilePath}
import Parsing.parseInputCoordinates
import ImageGeneration.executeImageGeneration

import java.net.{URI, URLEncoder}

object AppRunner {
  def run(args: CliArgs): Unit = {
    val swapFlag = args.swapFlag
    val downloadFlag = args.downloadFlag
    val browserFlag = args.browserFlag
    val imageSize = args.imageSize

    val inputCoordinatesOne = parseInputCoordinates(args.inputOne, swapFlag)
    val inputCoordinatesTwo = parseInputCoordinates(args.inputTwo, swapFlag)
    val inputCoordinatesThree = parseInputCoordinates(args.inputThree, swapFlag)
    val inputCoordinatesFour = parseInputCoordinates(args.inputFour, swapFlag)
    val inputCoordinatesFive = parseInputCoordinates(args.inputFive, swapFlag)

    val featureCollection = createFeatureCollection(
      inputCoordinatesOne,
      Blue,
      inputCoordinatesTwo,
      Red,
      inputCoordinatesThree,
      Purple,
      inputCoordinatesFour,
      Yellow,
      inputCoordinatesFive,
      Green
    )

    val encodedJsonString =
      URLEncoder.encode(featureCollection.toString(), "UTF-8")

    val imageWidth = imageSize
    val imageHeight = imageSize
    val apiKey =
      "pk.eyJ1IjoiYXJuYXNiciIsImEiOiJjbG00dXY1MDAybGJrM2RwNnE2dmo1NW01In0.XC_idJ6KnMWc1N-MX-Ry7A"
    val outputPath = OutputFilePath("outputDir/output.png")

    val staticImageUri =
      new URI(
        s"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($encodedJsonString)/auto/${imageWidth}x$imageHeight?access_token=$apiKey"
      )

    executeImageGeneration(
      downloadFlag,
      browserFlag,
      outputPath,
      staticImageUri
    )
  }
}
