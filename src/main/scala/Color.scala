sealed trait Color {
  def hexValue: String
}
case object Red extends Color {
  val hexValue: String = "#ed333b"
}
case object Blue extends Color {
  val hexValue: String = "#1c71d8"
}
case object Green extends Color {
  val hexValue: String = "#2ec27e"
}
case object Yellow extends Color {
  val hexValue: String = "#f8e45c"
}
case object Purple extends Color {
  val hexValue: String = "#9141ac"
}
