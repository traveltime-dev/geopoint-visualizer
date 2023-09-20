import Models.{CoordinatesList, FilePath, Point}
import cats.effect.Sync
import cats.implicits.catsSyntaxApplicativeError
import io.circe.parser.parse

import scala.io.{BufferedSource, Source}

object Parsing {
  def readFile[F[_]: Sync](filePath: FilePath): F[BufferedSource] = {
    Sync[F]
      .delay(Source.fromFile(filePath.path))
      .handleErrorWith(e =>
        Sync[F].raiseError(
          new Exception(s"Failed to read file: ${filePath.path}", e)
        )
      )
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
