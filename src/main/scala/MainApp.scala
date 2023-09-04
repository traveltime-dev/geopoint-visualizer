import play.api.libs.json._
import Utils._

object MainApp {
  def main(args: Array[String]): Unit = {
    val inputCoordinates = parseInput(args)

    val featureCollection = createFeatureCollection(inputCoordinates)

    val geoJsonString = Json.prettyPrint(featureCollection)

    println(geoJsonString)
  }
}
