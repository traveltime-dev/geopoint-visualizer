import Implicits.runtime
import Models.{CliArgs, FilePath}
import TaskRunner._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object UnsafeRun {
  def unsafeRun(args: CliArgs): Unit = {
    import args._
    val outputPath = FilePath("outputDir/output.png")

    val colors =
      LazyList.continually(List(Red, Blue, Green, Yellow, Purple)).flatten

    if (futureFlag) {
      val future =
        runFuture(inputFile, outputPath, swapFlag, imageSize, apiToken, colors)
      Await.result(future, Duration.Inf)
    } else
      runIO(inputFile, outputPath, swapFlag, imageSize, apiToken, colors)
        .unsafeRunSync()
  }
}
