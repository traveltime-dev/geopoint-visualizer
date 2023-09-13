import Models.OutputFilePath

import scala.sys.process._
import java.awt.Desktop
import java.net.URI

object ImageGeneration {
  private def openInBrowser(uri: URI): Unit = {
    if (Desktop.isDesktopSupported) {
      Desktop.getDesktop.browse(uri)
    }
  }

  private def downloadImage(outputPath: OutputFilePath, url: URI): Unit = {
    s"curl -o $outputPath $url".!
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
          uri
        ) //If neither flag is picked, the default is to download the image
      case (true, false) => downloadImage(outputPath, uri)
      case (false, true) => openInBrowser(uri)
      case (true, true) =>
        downloadImage(outputPath, uri)
        openInBrowser(uri)
    }
  }
}
