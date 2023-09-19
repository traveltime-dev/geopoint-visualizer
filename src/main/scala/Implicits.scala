import cats.effect.unsafe.IORuntime

object Implicits {
  implicit val runtime: IORuntime = IORuntime.global
}
