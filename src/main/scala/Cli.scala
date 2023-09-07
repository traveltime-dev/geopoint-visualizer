import Models.CliArgs
import cats.implicits.catsSyntaxTuple6Semigroupal
import com.monovore.decline._

object Cli {
  private val swap: Opts[String] = Opts
    .option[String](
      "swap",
      help = "`true` if latitude and longitude should be swapped"
    )
    .withDefault("false")

  private val inputCoordsOne: Opts[String] =
    Opts.option[String]("arg1", help = "Input coordinates")

  private val inputCoordsTwo: Opts[String] =
    Opts
      .option[String]("arg2", help = "Input coordinates (optional)")
      .withDefault("")

  private val inputCoordsThree: Opts[String] =
    Opts
      .option[String]("arg3", help = "Input coordinates (optional)")
      .withDefault("")

  private val inputCoordsFour: Opts[String] =
    Opts
      .option[String]("arg4", help = "Input coordinates (optional)")
      .withDefault("")

  private val inputCoordsFive: Opts[String] =
    Opts
      .option[String]("arg5", help = "Input coordinates (optional)")
      .withDefault("")

  val command: Opts[CliArgs] = Opts.subcommand(
    Command(name = "plot", header = "Plots given points on a map")(
      (
        swap,
        inputCoordsOne,
        inputCoordsTwo,
        inputCoordsThree,
        inputCoordsFour,
        inputCoordsFive
      ).mapN(CliArgs)
    )
  )
}
