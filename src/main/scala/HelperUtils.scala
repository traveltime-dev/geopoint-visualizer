import FeatureCreation.createFeatureCollection
import Parsing.parseInputCoordinates

import java.net.{URI, URLEncoder}
import scala.io.BufferedSource

object HelperUtils {
  def generateStaticImageUri(
      fileSource: BufferedSource,
      swapFlag: Boolean,
      colors: LazyList[Color],
      imageSize: Int,
      apiToken: String
  ): URI = {
    val inputCoordinates = parseInputCoordinates(fileSource, swapFlag)
    val featureCollection = createFeatureCollection(inputCoordinates, colors)
    val encodedJsonString = URLEncoder.encode(featureCollection, "UTF-8")

    new URI(
      s"https://api.mapbox.com/styles/v1/mapbox/dark-v11/static/geojson($encodedJsonString)" +
        s"/auto/${imageSize}x$imageSize?access_token=$apiToken"
    )
  }
}
