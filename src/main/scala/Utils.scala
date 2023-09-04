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
      colorTwo: Color,
      inputCoordinatesThree: CoordinatesList,
      colorThree: Color,
      inputCoordinatesFour: CoordinatesList,
      colorFour: Color,
      inputCoordinatesFive: CoordinatesList,
      colorFive: Color
  ): JsValue = {
    val features = createFeatures(
      inputCoordinatesOne,
      colorOne
    ) ++ createFeatures(inputCoordinatesTwo, colorTwo) ++ createFeatures(
      inputCoordinatesThree,
      colorThree
    ) ++ createFeatures(inputCoordinatesFour, colorFour) ++ createFeatures(
      inputCoordinatesFive,
      colorFive
    )

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
          "marker-size" -> "large",
          "marker-symbol" -> "circle"
        ),
        "geometry" -> Json.obj(
          "type" -> "Point",
          "coordinates" -> coordinates
        )
      )
    }
  }
}
