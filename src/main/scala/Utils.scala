import Models._
import Codecs._
import play.api.libs.json.{JsValue, Json}

object Utils {
  def parseInput(
      input: Option[String],
      swap: Boolean
  ): CoordinatesList = {
    input.map(parseInputCoordinates(_, swap)).getOrElse(Nil)
  }

  private def parseInputCoordinates(
      input: String,
      swap: Boolean
  ): CoordinatesList = {
    val coordinatesList = input
      .stripPrefix("[[")
      .stripSuffix("]]")
      .split("],\\s*\\[")
      .toList

    coordinatesList.map { coordString =>
      val coordinates = coordString.split(",").map(_.trim.toDoubleOption).toList
      if (swap && coordinates.length >= 2) {
        List(coordinates(1), coordinates.head)
      } else {
        coordinates
      }
    }
  }

  def createFeatureCollection(
      inputCoordinatesOne: CoordinatesList,
      colorOne: Color,
      inputCoordinatesTwo: CoordinatesList,
      colorTwo: Color
  ): JsValue = {
    val features = createFeatures(
      inputCoordinatesOne,
      colorOne
    ) ++ createFeatures(inputCoordinatesTwo, colorTwo)

    Json.obj(
      "type" -> "FeatureCollection",
      "features" -> features
    )
  }

  private def createFeatures(
      inputCoordinates: CoordinatesList,
      color: Color
  ): List[JsValue] = {
    inputCoordinates.map { coordinates =>
      Json.obj(
        "type" -> "Feature",
        "properties" -> Json.obj(
          "marker-color" -> (color match {
            case Red  => "#ed333b"
            case Blue => "#1c71d8"
            case _    => "#77767b"
          }),
          "marker-size" -> "medium",
          "marker-symbol" -> "circle",
          "id" -> (color match {
            case Red  => 0
            case Blue => 1
            case _    => 2
          })
        ),
        "geometry" -> Json.obj(
          "type" -> "Point",
          "coordinates" -> coordinates
        )
      )
    }
  }
}
