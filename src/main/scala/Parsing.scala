import Models.{CoordinatesList, Point}
import io.circe.parser.parse

object Parsing {
  def parseInputCoordinates(
      input: String,
      swap: Boolean
  ): Option[CoordinatesList] = {
    val json = parse(input)

    json
      .flatMap(_.as[CoordinatesList].map { coordinatesList =>
        if (swap) {
          val swappedCoordinates =
            coordinatesList.points.map(point => Point(point.lng, point.lat))
          CoordinatesList(swappedCoordinates)
        } else {
          coordinatesList
        }
      })
      .toOption
  }
}
