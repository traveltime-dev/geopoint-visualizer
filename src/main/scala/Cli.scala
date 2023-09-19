import Models.{CliArgs, FilePath}
import cats.implicits.catsSyntaxTuple5Semigroupal
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
        swapFlag,
        downloadFlag,
        browserFlag,
        imageSize,
        inputFile
      ).mapN(CliArgs)
    )
  )
}
