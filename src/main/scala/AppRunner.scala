import Utils._
import java.net.URLEncoder
import scala.sys.process._
import Models.CliArgs

object AppRunner {
  def run(args: CliArgs): Unit = {
    val swapFlag = args.swap
    val downloadFlag = args.download
    val browserFlag = args.browser

    val inputCoordinatesOne = parseInputCoordinates(args.inputOne, swapFlag)
    val inputCoordinatesTwo = parseInputCoordinates(args.inputTwo, swapFlag)
    val inputCoordinatesThree = parseInputCoordinates(args.inputThree, swapFlag)
    val inputCoordinatesFour = parseInputCoordinates(args.inputFour, swapFlag)
    val inputCoordinatesFive = parseInputCoordinates(args.inputFive, swapFlag)

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

    val downloadCommand = s"curl -o $outputPath $staticImageUrl"

    executeImageGeneration(
      downloadFlag,
      browserFlag,
      downloadCommand,
      openInBrowser(staticImageUrl)
    )
  }
}
