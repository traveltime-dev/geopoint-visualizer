import Models.OutputFilePath
import sttp.client4.{DefaultSyncBackend, UriContext, asByteArray, basicRequest}

import java.awt.Desktop
import java.net.URI
import java.nio.file.{Files, Paths}
import cats.effect.IO

object ImageGeneration {
  private def openInBrowser(uri: URI): IO[Unit] = IO {
    if (Desktop.isDesktopSupported) {
      Desktop.getDesktop.browse(uri)
    }
  }

  private def downloadImage(outputPath: OutputFilePath, uri: URI): IO[Unit] =
    IO {
      basicRequest
        .get(uri"${uri.toString}")
        .response(asByteArray)
        .send(DefaultSyncBackend())
        .body match {
        case Left(message) => println(message)
        case Right(byteArray) =>
          Files.write(
            Paths.get(outputPath.path),
            byteArray
          )
      }
    }

  def executeImageGeneration(
      downloadFlag: Boolean,
      browserFlag: Boolean,
      outputPath: OutputFilePath,
      uri: URI
  ): IO[Unit] = {
    (downloadFlag, browserFlag) match {
      case (false, false) =>
        downloadImage(outputPath, uri).map(_ => ()) //default
      case (true, false) =>
        downloadImage(outputPath, uri).map(_ => ())
      case (false, true) =>
        openInBrowser(uri).map(_ => ())
      case (true, true) =>
        for {
          _ <- downloadImage(outputPath, uri)
          _ <- openInBrowser(uri)
        } yield ()
    }
  }
}
