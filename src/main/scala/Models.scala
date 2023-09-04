object Models {
  case class CoordinatesList(points: List[List[Option[Double]]]) {
    def map[U](f: List[Option[Double]] => U): List[U] = points.map(f)
  }
}
