import Models.CoordinatesList

import scala.language.implicitConversions

object Codecs {
  implicit def listToCoordinatesList(
      list: List[List[Option[Double]]]
  ): CoordinatesList = {
    CoordinatesList(list)
  }
}
