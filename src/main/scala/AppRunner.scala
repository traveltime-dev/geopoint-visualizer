import FeatureCreation.createFeatureCollection
import Models.{CliArgs, FilePath}
import Parsing.{parseInputCoordinates, readFile}
import ImageGeneration.executeImageGeneration
import cats.effect.{IO, Sync}
import java.net.{URI, URLEncoder}

object AppRunner {
  def runIO(args: CliArgs): IO[Unit] = {
    val swapFlag = args.swapFlag
    val downloadFlag = args.downloadFlag
    val browserFlag = args.browserFlag
    val imageSize = args.imageSize
    val inputFile = args.inputFile
    val apiToken = args.apiToken
    val outputPath = FilePath("outputDir/output.png")

    val colors =
      LazyList.continually(List(Red, Blue, Green, Yellow, Purple)).flatten

    (for {
      fileSource <- readFile[IO](inputFile)
      inputCoordinates = parseInputCoordinates(fileSource, swapFlag)
      featureCollection = createFeatureCollection(inputCoordinates, colors)
      encodedJsonString = URLEncoder.encode(featureCollection, "UTF-8")
      staticImageUri = new URI(
        s"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($encodedJsonString)" +
          s"/auto/${imageSize}x$imageSize?access_token=$apiToken"
      )
      _ <- executeImageGeneration[IO](
        downloadFlag,
        browserFlag,
        outputPath,
        staticImageUri
      )
    } yield ()).attempt.flatMap {
      case Left(e) =>
        Sync[IO].delay(println(s"ERROR: ${e.getMessage}"))
      case Right(_) =>
        Sync[IO].pure(())
    }
  }
}
