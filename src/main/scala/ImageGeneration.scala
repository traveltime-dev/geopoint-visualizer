import Models.FilePath
import sttp.client4.{DefaultSyncBackend, UriContext, asByteArray, basicRequest}

import java.awt.Desktop
import java.net.URI
import java.nio.file.{Files, Paths}
import cats.effect.Sync
import cats.implicits.catsSyntaxTuple2Semigroupal

object ImageGeneration {
  private def openInBrowser[F[_]: Sync](uri: URI): F[Unit] = Sync[F].delay {
    if (Desktop.isDesktopSupported) {
      Desktop.getDesktop.browse(uri)
    }
  }

  private def downloadImage[F[_]: Sync](
      outputPath: FilePath,
      uri: URI
  ): F[Unit] = {
    val request = basicRequest
      .get(uri"${uri.toString}")
      .response(asByteArray)

    Sync[F].delay {
      request.send(DefaultSyncBackend()).body match {
        case Left(message) => println(message)
        case Right(byteArray) =>
          Files.write(Paths.get(outputPath.path), byteArray)
      }
    }
  }

  def executeImageGeneration[F[_]: Sync](
      downloadFlag: Boolean,
      browserFlag: Boolean,
      outputPath: FilePath,
      uri: URI
  ): F[Unit] = {
    val downloadF: F[Unit] =
      if (downloadFlag) downloadImage(outputPath, uri) else Sync[F].unit
    val browserF: F[Unit] =
      if (browserFlag) openInBrowser(uri) else Sync[F].unit

    (downloadF, browserF).mapN((_, _) => ())
  }
}
