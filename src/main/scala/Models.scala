import io.circe._

object Models {
  case class CoordinatesList(points: List[Point]) {
    def map[U](f: Point => U): List[U] = points.map(f)
  }

  case class Point(lat: Double, lng: Double)

  case class CliArgs(
      apiToken: String,
      swapFlag: Boolean,
      futureFlag: Boolean,
      imageSize: Int,
      inputFile: FilePath
  )

  object CoordinatesList {
    implicit val pointDecoder: Decoder[Point] = (c: HCursor) =>
      for {
        lat <- c.downN(0).as[Double]
        lng <- c.downN(1).as[Double]
      } yield Point(lat, lng)

    implicit val decoder: Decoder[CoordinatesList] = (c: HCursor) =>
      c.as[List[Point]].map(CoordinatesList.apply)
  }

  case class FilePath(path: String)
}
