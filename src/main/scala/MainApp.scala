import play.api.libs.json._
import Utils._

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

    val geoJsonString = Json.prettyPrint(featureCollection)

    println(geoJsonString)
  }
}
