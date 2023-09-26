import Models.FilePath
import Parsing.readFile
import ImageGeneration.downloadImage
import HelperUtils.generateStaticImageUri
import cats.effect.{IO, Sync}
import sttp.client3.HttpClientFutureBackend
import sttp.client3.httpclient.cats.HttpClientCatsBackend

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object TaskRunner {
  def runIO(
      inputFile: FilePath,
      outputPath: FilePath,
      swapFlag: Boolean,
      imageSize: Int,
      apiToken: String,
      colors: LazyList[Color]
  ): IO[Unit] = {
    (for {
      fileSource <- readFile[IO](inputFile)
      staticImageUri = generateStaticImageUri(
        fileSource,
        swapFlag,
        colors,
        imageSize,
        apiToken
      )

      _ <- HttpClientCatsBackend.resource[IO]().use { backend =>
        downloadImage(outputPath.path, staticImageUri, backend)
      }

    } yield ()).attempt
      .flatMap {
        case Left(e) =>
          Sync[IO].delay(println(s"ERROR: ${e.getMessage}"))
        case Right(_) =>
          Sync[IO].pure(())
      }
  }

  def runFuture(
      inputFile: FilePath,
      outputPath: FilePath,
      swapFlag: Boolean,
      imageSize: Int,
      apiToken: String,
      colors: LazyList[Color]
  )(implicit
      ec: ExecutionContext
  ): Future[Unit] = {
    (for {
      fileSource <- readFile[Future](inputFile)
      staticImageUri = generateStaticImageUri(
        fileSource,
        swapFlag,
        colors,
        imageSize,
        apiToken
      )
      _ <- downloadImage(
        outputPath.path,
        staticImageUri,
        HttpClientFutureBackend()
      )
    } yield ()).transform {
      case Success(_) => Success(())
      case Failure(e) =>
        println(s"ERROR: ${e.getMessage}")
        Failure(e)
    }
  }
}
