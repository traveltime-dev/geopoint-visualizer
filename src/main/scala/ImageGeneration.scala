import Models.FilePath
import cats.MonadError
import sttp.client3.{SttpBackend, UriContext, asByteArray, basicRequest}

import java.awt.Desktop
import java.net.URI
import java.nio.file.{Files, Paths}
import cats.implicits.{catsSyntaxTuple2Semigroupal, toFlatMapOps}

object ImageGeneration {
  private def openInBrowser[F[_]](
      uri: URI
  )(implicit ME: MonadError[F, Throwable]): F[Unit] = {
    if (Desktop.isDesktopSupported)
      ME.catchNonFatal(Desktop.getDesktop.browse(uri))
    else ME.raiseError(new Exception("Desktop is not supported"))
  }

  private def downloadImage[F[_], P](
      outputPath: String,
      uri: URI,
      backend: SttpBackend[F, P]
  )(implicit
      ME: MonadError[F, Throwable]
  ): F[Unit] = {
    val request = basicRequest.get(uri"${uri.toString}").response(asByteArray)

    request.send(backend).flatMap { response =>
      response.body match {
        case Left(message) =>
          ME.raiseError(new Exception(s"Failed to download image: $message"))
        case Right(byteArray) =>
          ME.catchNonFatal(Files.write(Paths.get(outputPath), byteArray))
      }
    }
  }

  def executeImageGeneration[F[_], P](
      downloadFlag: Boolean,
      browserFlag: Boolean,
      outputPath: FilePath,
      uri: URI,
      backend: SttpBackend[F, P]
  )(implicit ME: MonadError[F, Throwable]): F[Unit] = {
    val downloadF: F[Unit] =
      if (downloadFlag) downloadImage(outputPath.path, uri, backend)
      else ME.pure(())

    val browserF: F[Unit] =
      if (browserFlag) openInBrowser(uri)
      else ME.pure(())

    (downloadF, browserF).mapN((_, _) => ())
  }
}
