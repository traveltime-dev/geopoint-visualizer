import Utils._
import java.awt.Desktop
import java.net.{URI, URLEncoder}

object MainApp {
  def main(args: Array[String]): Unit = {
    val swap: Boolean = args.headOption match {
      case Some(value) => if (value == "swap=true") true else false
      case None        => false
    }

    val inputCoordinatesOne = parseInput(args.lift(1), swap)
    val inputCoordinatesTwo = parseInput(args.lift(2), swap)
    val inputCoordinatesThree = parseInput(args.lift(3), swap)
    val inputCoordinatesFour = parseInput(args.lift(4), swap)
    val inputCoordinatesFive = parseInput(args.lift(5), swap)

    val featureCollection = createFeatureCollection(
      inputCoordinatesOne,
      Blue,
      inputCoordinatesTwo,
      Red,
      inputCoordinatesThree,
      Green,
      inputCoordinatesFour,
      Yellow,
      inputCoordinatesFive,
      Purple
    )

    println(featureCollection)

    val encodedJsonString =
      URLEncoder.encode(featureCollection.toString(), "UTF-8")

    val imageWidth = 1200
    val imageHeight = 1200
    val apiKey =
      "pk.eyJ1IjoiYXJuYXNiciIsImEiOiJjbG00dXY1MDAybGJrM2RwNnE2dmo1NW01In0.XC_idJ6KnMWc1N-MX-Ry7A"

    val staticImageUrl =
      s"https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/geojson($encodedJsonString)/auto/${imageWidth}x$imageHeight?access_token=$apiKey"

    if (Desktop.isDesktopSupported) {
      Desktop.getDesktop.browse(new URI(staticImageUrl))
    }
  }
}
