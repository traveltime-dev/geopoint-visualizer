import Models._
import io.circe.{Json, Printer}
import io.circe.syntax._

object FeatureCreation {
  def createFeatureCollection(
      inputCoordinatesOneOpt: Option[CoordinatesList],
      colorOne: Color,
      inputCoordinatesTwoOpt: Option[CoordinatesList],
      colorTwo: Color,
      inputCoordinatesThreeOpt: Option[CoordinatesList],
      colorThree: Color,
      inputCoordinatesFourOpt: Option[CoordinatesList],
      colorFour: Color,
      inputCoordinatesFiveOpt: Option[CoordinatesList],
      colorFive: Color
  ): String = {
    val features = Seq(
      createFeatures(inputCoordinatesOneOpt, colorOne),
      createFeatures(inputCoordinatesTwoOpt, colorTwo),
      createFeatures(inputCoordinatesThreeOpt, colorThree),
      createFeatures(inputCoordinatesFourOpt, colorFour),
      createFeatures(inputCoordinatesFiveOpt, colorFive)
    ).flatten

    val featureCollection = Json.obj(
      "type" -> "FeatureCollection".asJson,
      "features" -> features.asJson
    )

    featureCollection.printWith(Printer.noSpaces)
  }

  private def createFeatures(
      inputCoordinatesOpt: Option[CoordinatesList],
      color: Color
  ): List[Json] = {
    for {
      inputCoordinates <- inputCoordinatesOpt.toList
      coordinates <- inputCoordinates.points.zipWithIndex
    } yield Json.obj(
      "type" -> "Feature".asJson,
      "properties" -> Json.obj(
        "marker-color" -> color.hexValue.asJson,
        "marker-size" -> "large".asJson,
        "marker-symbol" -> (coordinates._2 + 1).toString.asJson
      ),
      "geometry" -> Json.obj(
        "type" -> "Point".asJson,
        "coordinates" -> List(
          coordinates._1.lat.asJson,
          coordinates._1.lng.asJson
        ).asJson
      )
    )
  }
}
