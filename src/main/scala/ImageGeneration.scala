import Models.OutputFilePath
import sttp.client4.{DefaultSyncBackend, UriContext, asByteArray, basicRequest}
import java.awt.Desktop
import java.net.URI
import java.nio.file.{Files, Paths}

object ImageGeneration {
  private def openInBrowser(uri: URI): Unit = {
    if (Desktop.isDesktopSupported) {
      Desktop.getDesktop.browse(uri)
    }
  }

  private def downloadImage(outputPath: OutputFilePath, uri: URI): Unit = {
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
  ): Unit = {
    (downloadFlag, browserFlag) match {
      case (false, false) =>
        downloadImage(
          outputPath,
          uri: URI
        ) //If neither flag is picked, the default is to download the image
      case (true, false) => downloadImage(outputPath, uri)
      case (false, true) => openInBrowser(uri)
      case (true, true) =>
        downloadImage(outputPath, uri)
        openInBrowser(uri)
    }
  }
}
