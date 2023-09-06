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
    val pattern = "\\[(\\d+\\.\\d+), (\\d+\\.\\d+)]".r

    pattern
      .findAllMatchIn(input)
      .map { m =>
        val coord1 = m.group(1).toDoubleOption
        val coord2 = m.group(2).toDoubleOption
        if (swap) List(coord2, coord1)
        else List(coord1, coord2)
      }
      .toList
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
          "marker-color" -> color.hexValue,
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
