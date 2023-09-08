import Models._
import play.api.libs.json.{JsValue, Json}

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
  ): JsValue = {
    val features = Seq(
      createFeatures(inputCoordinatesOneOpt, colorOne),
      createFeatures(inputCoordinatesTwoOpt, colorTwo),
      createFeatures(inputCoordinatesThreeOpt, colorThree),
      createFeatures(inputCoordinatesFourOpt, colorFour),
      createFeatures(inputCoordinatesFiveOpt, colorFive)
    ).flatten

    Json.obj(
      "type" -> "FeatureCollection",
      "features" -> features
    )
  }

  private def createFeatures(
      inputCoordinatesOpt: Option[CoordinatesList],
      color: Color
  ): List[JsValue] = {
    for {
      inputCoordinates <- inputCoordinatesOpt.toList
      coordinates <- inputCoordinates.points
    } yield {
      Json.obj(
        "type" -> "Feature",
        "properties" -> Json.obj(
          "marker-color" -> color.hexValue,
          "marker-size" -> "large",
          "marker-symbol" -> "circle"
        ),
        "geometry" -> Json.obj(
          "type" -> "Point",
          "coordinates" -> List(coordinates.lat, coordinates.lng)
        )
      )
    }
  }
}
