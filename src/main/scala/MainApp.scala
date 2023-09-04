import play.api.libs.json._
import Utils._

object MainApp {
  def main(args: Array[String]): Unit = {
    val inputCoordinatesOne = parseInput(args.headOption)
    val inputCoordinatesTwo = parseInput(args.lift(1))

    val featureCollection = createFeatureCollection(
      inputCoordinatesOne,
      Blue,
      inputCoordinatesTwo,
      Red
    )

    val geoJsonString = Json.prettyPrint(featureCollection)

    println(geoJsonString)
  }
}
