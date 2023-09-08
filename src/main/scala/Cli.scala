import Models.CliArgs
import cats.implicits.catsSyntaxTuple8Semigroupal
import com.monovore.decline._

object Cli {
  private val swapFlag: Opts[Boolean] = Opts
    .flag(
      "swap",
      help = "Flag to indicate if latitude and longitude should be swapped"
    )
    .orFalse

  private val downloadFlag: Opts[Boolean] = Opts
    .flag(
      "download",
      help = "Flag to indicate if image should be downloaded to outputDir"
    )
    .orFalse

  private val browserFlag: Opts[Boolean] = Opts
    .flag(
      "browser",
      help = "Flag to indicate if image should be opened in browser"
    )
    .orFalse

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
        swapFlag,
        downloadFlag,
        browserFlag,
        inputCoordsOne,
        inputCoordsTwo,
        inputCoordsThree,
        inputCoordsFour,
        inputCoordsFive
      ).mapN(CliArgs)
    )
  )
}
