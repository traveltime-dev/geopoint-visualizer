import AppRunner.run
import com.monovore.decline.CommandApp

object MainApp
    extends CommandApp(
      name = "geopoint-visualizer",
      header = "Plots given points on a map",
      main = {
        Cli.command.map(args => run(args))
      }
    )
