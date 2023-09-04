import play.api.libs.json._

object MainApp {
  def main(args: Array[String]): Unit = {
    val inputCoordinates = List(
      List(12.4324, 21.23123),
      List(12.5324, 21.23123),
      List(13.4324, 21.23123)
    )

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

    val featureCollection = Json.obj(
      "type" -> "FeatureCollection",
      "features" -> features
    )

    val geoJsonString = Json.prettyPrint(featureCollection)

    println(geoJsonString)
  }
}
