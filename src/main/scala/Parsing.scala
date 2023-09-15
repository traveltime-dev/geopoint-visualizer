import Models.{CoordinatesList, FilePath, Point}
import io.circe.parser.parse

import scala.io.{BufferedSource, Source}

object Parsing {
  def readFile(filePath: FilePath): BufferedSource = {
    Source.fromFile(filePath.path)
  }

  def parseInputCoordinates(
      source: BufferedSource,
      swap: Boolean
  ): Option[List[CoordinatesList]] = {
    val parsedJson = parse(source.mkString)

    parsedJson
      .flatMap(_.as[List[CoordinatesList]].map { listCoordinatesList =>
        listCoordinatesList.map { coordinatesList =>
          if (swap) {
            val swappedCoordinates =
              coordinatesList.points.map(point => Point(point.lng, point.lat))
            CoordinatesList(swappedCoordinates)
          } else {
            coordinatesList
          }
        }
      })
      .toOption
  }
}
