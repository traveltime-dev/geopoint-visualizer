import FeatureCreation.createFeatureCollection
import Models.{CliArgs, FilePath}
import Parsing.{parseInputCoordinates, readFile}
import ImageGeneration.executeImageGeneration
import cats.effect.IO
import java.net.{URI, URLEncoder}

object AppRunner {
  def runIO(args: CliArgs): IO[Unit] = {
    val swapFlag = args.swapFlag
    val downloadFlag = args.downloadFlag
    val browserFlag = args.browserFlag
    val imageSize = args.imageSize
    val inputFile = args.inputFile
    val outputPath = FilePath("outputDir/output.png")

    val colors =
      LazyList.continually(List(Red, Blue, Green, Yellow, Purple)).flatten

    for {
      fileSource <- readFile[IO](inputFile)
      inputCoordinates = parseInputCoordinates(fileSource, swapFlag)
      featureCollection = createFeatureCollection(inputCoordinates, colors)
      encodedJsonString = URLEncoder.encode(featureCollection, "UTF-8")
      staticImageUri = new URI(
        s"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($encodedJsonString)" +
          s"/auto/${imageSize}x$imageSize?access_token=$getApiKey"
      )
      _ <- executeImageGeneration[IO](
        downloadFlag,
        browserFlag,
        outputPath,
        staticImageUri
      )
    } yield ()
  }

  private def getApiKey: String = {
    //TODO: fetch api key from env variable or something similar
    "pk.eyJ1IjoiYXJuYXNiciIsImEiOiJjbG00dXY1MDAybGJrM2RwNnE2dmo1NW01In0.XC_idJ6KnMWc1N-MX-Ry7A"
  }
}
