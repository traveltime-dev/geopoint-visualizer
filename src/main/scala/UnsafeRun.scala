import Implicits.runtime
import Models.CliArgs
import TaskRunner._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object UnsafeRun {
  def unsafeRun(args: CliArgs): Unit = {
    val futureFlag = args.futureFlag

    val colors =
      LazyList.continually(List(Red, Blue, Green, Yellow, Purple)).flatten

    if (futureFlag) {
      val future = runFuture(args, colors)
      Await.result(future, Duration.Inf)
    } else runIO(args, colors).unsafeRunSync()
  }
}
