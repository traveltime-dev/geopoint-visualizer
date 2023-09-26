import FeatureCreation.createFeatureCollection
import Models.{CliArgs, FilePath}
import Parsing.{parseInputCoordinates, readFile}
import ImageGeneration.downloadImage
import cats.effect.{IO, Sync}
import sttp.client3.HttpClientFutureBackend
import sttp.client3.httpclient.cats.HttpClientCatsBackend

import java.net.{URI, URLEncoder}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object TaskRunner {
  def runIO(args: CliArgs, colors: LazyList[Color]): IO[Unit] = {
    val swapFlag = args.swapFlag
    val imageSize = args.imageSize
    val inputFile = args.inputFile
    val apiToken = args.apiToken
    val outputPath = FilePath("outputDir/output.png")

    (for {
      fileSource <- readFile[IO](inputFile)
      inputCoordinates = parseInputCoordinates(fileSource, swapFlag)
      featureCollection = createFeatureCollection(inputCoordinates, colors)
      encodedJsonString = URLEncoder.encode(featureCollection, "UTF-8")
      staticImageUri = new URI(
        s"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($encodedJsonString)" +
          s"/auto/${imageSize}x$imageSize?access_token=$apiToken"
      )

      _ <- HttpClientCatsBackend.resource[IO]().use { backend =>
        downloadImage(outputPath.path, staticImageUri, backend)
      }

    } yield ()).attempt.flatMap {
      case Left(e) =>
        Sync[IO].delay(println(s"ERROR: ${e.getMessage}"))
      case Right(_) =>
        Sync[IO].pure(())
    }
  }

  def runFuture(args: CliArgs, colors: LazyList[Color])(implicit
      ec: ExecutionContext
  ): Future[Unit] = {
    val swapFlag = args.swapFlag
    val imageSize = args.imageSize
    val inputFile = args.inputFile
    val apiToken = args.apiToken
    val outputPath = FilePath("outputDir/output.png")

    for {
      fileSource <- readFile[Future](inputFile)
      inputCoordinates = parseInputCoordinates(fileSource, swapFlag)
      featureCollection = createFeatureCollection(inputCoordinates, colors)
      encodedJsonString = URLEncoder.encode(featureCollection, "UTF-8")
      staticImageUri = new URI(
        s"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($encodedJsonString)" +
          s"/auto/${imageSize}x$imageSize?access_token=$apiToken"
      )

      _ <- downloadImage(
        outputPath.path,
        staticImageUri,
        HttpClientFutureBackend()
      )
    } yield ()
  }.transform {
    case Success(_) => Success(())
    case Failure(e) =>
      println(s"ERROR: ${e.getMessage}")
      Failure(e)
  }
}
