import Models.FilePath
import cats.NonEmptyParallel
import sttp.client3.{UriContext, asByteArray, basicRequest}

import java.awt.Desktop
import java.net.URI
import java.nio.file.{Files, Paths}
import cats.effect.{Async, Sync}
import cats.implicits.{
  catsSyntaxApplicativeError,
  catsSyntaxTuple2Parallel,
  toFlatMapOps
}
import sttp.client3.httpclient.cats.HttpClientCatsBackend

object ImageGeneration {
  private def openInBrowser[F[_]: Sync](uri: URI): F[Unit] = {
    Sync[F]
      .delay(if (Desktop.isDesktopSupported) Desktop.getDesktop.browse(uri))
      .handleErrorWith { error =>
        Sync[F].delay(
          println(s"Failed to open URI in browser: ${error.getMessage}")
        )
      }
  }

  private def downloadImage[F[_]: Async](
      outputPath: FilePath,
      uri: URI
  ): F[Unit] = {
    val request = basicRequest
      .get(uri"${uri.toString}")
      .response(asByteArray)

    HttpClientCatsBackend.resource[F]().use { backend =>
      request.send(backend).flatMap { response =>
        response.body match {
          case Left(message) =>
            Async[F].raiseError[Unit](
              new Exception(s"Failed to download image: $message")
            )
          case Right(byteArray) =>
            Async[F].delay(Files.write(Paths.get(outputPath.path), byteArray))
        }
      }
    }
  }

  def executeImageGeneration[F[_]: Async: NonEmptyParallel](
      downloadFlag: Boolean,
      browserFlag: Boolean,
      outputPath: FilePath,
      uri: URI
  ): F[Unit] = {
    val downloadF: F[Unit] =
      if (downloadFlag) downloadImage(outputPath, uri) else Async[F].unit
    val browserF: F[Unit] =
      if (browserFlag) openInBrowser(uri) else Async[F].unit

    (downloadF, browserF).parMapN((_, _) => ())
  }
}
