import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import FeatureCreation._
import Models._
import io.circe.{Json, Printer}
import io.circe.syntax.EncoderOps

class FeatureCreationTest extends AnyFlatSpec with Matchers {
  "createFeatureCollection" should "return a valid FeatureCollection JSON string" in {
    val coordinatesList = Some(
      List(
        CoordinatesList(List(Point(1, 2)))
      )
    )
    val colors = LazyList(Red)

    val result =
      createFeatureCollection(coordinatesList, colors)

    val expectedJson = Json.obj(
      "type" -> "FeatureCollection".asJson,
      "features" -> List(
        Json.obj(
          "type" -> "Feature".asJson,
          "properties" -> Json.obj(
            "marker-color" -> Red.hexValue.asJson,
            "marker-size" -> "large".asJson,
            "marker-symbol" -> "1".asJson
          ),
          "geometry" -> Json.obj(
            "type" -> "Point".asJson,
            "coordinates" -> List(1.0, 2.0).asJson
          )
        )
      ).asJson
    )

    val expectedString = expectedJson.printWith(Printer.noSpaces)
    result shouldEqual expectedString
  }

  "createFeatures" should "return a list of Features as Json objects" in {
    val coordinatesList = Some(
      List(
        CoordinatesList(List(Point(1, 2), Point(3, 4)))
      )
    )
    val colors = LazyList(Red)

    val result = createFeatures(coordinatesList, colors)

    val expected = List(
      Json.obj(
        "type" -> "Feature".asJson,
        "properties" -> Json.obj(
          "marker-color" -> Red.hexValue.asJson,
          "marker-size" -> "large".asJson,
          "marker-symbol" -> "1".asJson
        ),
        "geometry" -> Json.obj(
          "type" -> "Point".asJson,
          "coordinates" -> List(1.0, 2.0).asJson
        )
      ),
      Json.obj(
        "type" -> "Feature".asJson,
        "properties" -> Json.obj(
          "marker-color" -> Red.hexValue.asJson,
          "marker-size" -> "large".asJson,
          "marker-symbol" -> "2".asJson
        ),
        "geometry" -> Json.obj(
          "type" -> "Point".asJson,
          "coordinates" -> List(3.0, 4.0).asJson
        )
      )
    )
    result shouldEqual expected
  }
}
