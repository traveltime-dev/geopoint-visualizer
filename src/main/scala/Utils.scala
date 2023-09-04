import play.api.libs.json.{JsValue, Json}

object Utils {
  def parseInput(args: Array[String]): List[List[Double]] = {
    args.headOption.map(parseInputCoordinates).getOrElse(Nil)
  }

  private def parseInputCoordinates(input: String): List[List[Double]] = {
    val coordinatesList = input
      .stripPrefix("[[")
      .stripSuffix("]]")
      .split("],\\s*\\[")
      .toList

    coordinatesList.map { coordString =>
      coordString.split(",").map(_.trim.toDouble).toList
    }
  }

  def createFeatureCollection(
      inputCoordinates: List[List[Double]]
  ): JsValue = {
    val features = inputCoordinates.map { coordinates =>
      Json.obj(
        "type" -> "Feature",
        "properties" -> Json.obj(),
        "geometry" -> Json.obj(
          "type" -> "Point",
          "coordinates" -> coordinates
        )
      )
    }

    Json.obj(
      "type" -> "FeatureCollection",
      "features" -> features
    )
  }
}
