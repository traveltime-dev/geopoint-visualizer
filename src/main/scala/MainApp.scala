import play.api.libs.json._
import Utils._

object MainApp {
  def main(args: Array[String]): Unit = {
    val swap: Boolean = args.lift(2) match {
      case Some(value) => if (value == "swap") true else false
      case None        => false
    }

    val inputCoordinatesOne = parseInput(args.headOption, swap)
    val inputCoordinatesTwo = parseInput(args.lift(1), swap)

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
