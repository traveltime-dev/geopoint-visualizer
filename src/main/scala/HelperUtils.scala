import FeatureCreation.createFeatureCollection
import Parsing.parseInputCoordinates
import sttp.client3.UriContext
import scala.io.BufferedSource
import sttp.model.Uri

object HelperUtils {
  def generateStaticImageUri(
      fileSource: BufferedSource,
      swapFlag: Boolean,
      colors: LazyList[Color],
      imageSize: Int,
      apiToken: String
  ): Uri = {
    val inputCoordinates = parseInputCoordinates(fileSource, swapFlag)
    val featureCollection = createFeatureCollection(inputCoordinates, colors)

    uri"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($featureCollection)/auto/${imageSize}x$imageSize?access_token=$apiToken"
  }
}
