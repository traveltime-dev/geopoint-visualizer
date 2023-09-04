sealed trait Color {
  def hexValue: String
  def id: Int
}
case object Red extends Color {
  val hexValue: String = "#ed333b"
  val id = 0
}
case object Blue extends Color {
  val hexValue: String = "#1c71d8"
  val id = 1
}
