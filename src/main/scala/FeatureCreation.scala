import Models._
import io.circe.{Json, Printer}
import io.circe.syntax._

object FeatureCreation {
  def createFeatureCollection(
      inputCoordinatesListOpt: Option[List[CoordinatesList]],
      colors: LazyList[Color]
  ): String = {
    val features = createFeatures(inputCoordinatesListOpt, colors)

    val featureCollection = Json.obj(
      "type" -> "FeatureCollection".asJson,
      "features" -> features.asJson
    )

    featureCollection.printWith(Printer.noSpaces)
  }

  def createFeatures(
      inputCoordinatesListOpt: Option[List[CoordinatesList]],
      colors: LazyList[Color]
  ): List[Json] = {
    inputCoordinatesListOpt.toList.flatMap { inputCoordinatesList =>
      val colorIterator = colors.iterator
      for {
        inputCoordinates <- inputCoordinatesList
        color = colorIterator.next()
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
}
