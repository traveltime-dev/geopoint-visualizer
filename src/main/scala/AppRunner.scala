import Utils._
import java.net.URLEncoder
import scala.sys.process._

object AppRunner {
  def run(args: Array[String]): Unit = {
    val swap: Boolean = args.headOption match {
      case Some(value) => value == "swap=true"
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

    val encodedJsonString =
      URLEncoder.encode(featureCollection.toString(), "UTF-8")

    val imageWidth = 1280
    val imageHeight = 1280
    val apiKey =
      "pk.eyJ1IjoiYXJuYXNiciIsImEiOiJjbG00dXY1MDAybGJrM2RwNnE2dmo1NW01In0.XC_idJ6KnMWc1N-MX-Ry7A"
    val outputPath = "outputDir/output.png"

    val staticImageUrl =
      s"https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/geojson($encodedJsonString)/auto/${imageWidth}x$imageHeight?access_token=$apiKey"

    val cmd = s"curl -o $outputPath $staticImageUrl"
    cmd.!

    //Uncomment this line if you want the output image to be opened in the browser automatically
    //openInBrowser(staticImageUrl)
  }
}
