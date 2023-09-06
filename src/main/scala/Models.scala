import io.circe._

object Models {
  case class CoordinatesList(points: List[List[Option[Double]]]) {
    def map[U](f: List[Option[Double]] => U): List[U] = points.map(f)
  }

  object CoordinatesList {
    implicit val decoder: Decoder[CoordinatesList] = (c: HCursor) =>
      for {
        rows <- c.as[List[List[Option[Double]]]]
      } yield CoordinatesList(rows)
  }
}
