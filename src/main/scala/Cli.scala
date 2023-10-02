import Models.{CliArgs, FilePath}
import cats.implicits.catsSyntaxTuple5Semigroupal
import com.monovore.decline._

object Cli {
  private val apiToken: Opts[String] =
    Opts
      .option[String](
        "token",
        help = "Mapbox api token"
      )

  private val swapFlag: Opts[Boolean] = Opts
    .flag(
      "swap",
      help = "Flag to indicate if latitude and longitude should be swapped"
    )
    .orFalse

  private val futureFlag: Opts[Boolean] = Opts
    .flag(
      "future",
      help =
        "Flag to indicate if app should be run using Futures instead of IO (cats effect)"
    )
    .orFalse

  private val imageSize: Opts[Int] =
    Opts
      .option[Int]("img_size", help = "Image size 1-1280 (default - 1000)")
      .withDefault(1000)

  private val inputFile: Opts[FilePath] =
    Opts
      .option[String](
        "input",
        help = "Input file path (default - inputDir/input.json)"
      )
      .map(FilePath)
      .withDefault(FilePath("inputDir/input.json"))

  val command: Opts[CliArgs] = Opts.subcommand(
    Command(name = "plot", header = "Plots given points on a map")(
      (
        apiToken,
        swapFlag,
        futureFlag,
        imageSize,
        inputFile
      ).mapN(CliArgs)
    )
  )
}
