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
case object Green extends Color {
  val hexValue: String = "#2ec27e"
  val id = 2
}
case object Yellow extends Color {
  val hexValue: String = "#f8e45c"
  val id = 3
}
case object Purple extends Color {
  val hexValue: String = "#9141ac"
  val id = 4
}
