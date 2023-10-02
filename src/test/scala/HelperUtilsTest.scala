import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import sttp.model.Uri

import java.io.ByteArrayInputStream
import java.net.URI
import scala.io.{BufferedSource, Source}

class HelperUtilsTest extends AnyFlatSpec with Matchers {
  def mockBufferedSource(content: String): BufferedSource = {
    val stream = new ByteArrayInputStream(content.getBytes)
    Source.fromInputStream(stream)
  }

  "generateStaticImageUri" should "generate correct URI" in {
    val testFileContent = """Any Json"""
    val bufferedSource = mockBufferedSource(testFileContent)
    val swapFlag = false
    val colors = LazyList(Red)
    val imageSize = 400
    val apiToken = "your-api-token"

    val uri: Uri = HelperUtils.generateStaticImageUri(
      bufferedSource,
      swapFlag,
      colors,
      imageSize,
      apiToken
    )

    uri shouldBe a[Uri]
    uri.toString should include(
      "https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson("
    )
    uri.toString should include(
      s"/auto/${imageSize}x$imageSize?access_token=$apiToken"
    )
  }
}
