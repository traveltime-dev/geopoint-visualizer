import scala.sys.process._
import java.awt.Desktop
import java.net.URI

object ImageGeneration {
  private def openInBrowser(url: String): Unit = {
    if (Desktop.isDesktopSupported) {
      Desktop.getDesktop.browse(new URI(url))
    }
  }

  private def downloadImage(outputPath: String, url: String): Unit = {
    s"curl -o $outputPath $url".!
  }

  def executeImageGeneration(
      downloadFlag: Boolean,
      browserFlag: Boolean,
      outputPath: String,
      url: String
  ): Any = {
    (downloadFlag, browserFlag) match {
      case (false, false) =>
        downloadImage(
          outputPath,
          url
        ) //If neither flag is picked, the default is to download the image
      case (true, false) => downloadImage(outputPath, url)
      case (false, true) => openInBrowser(url)
      case (true, true) =>
        downloadImage(outputPath, url)
        openInBrowser(url)
    }
  }
}
