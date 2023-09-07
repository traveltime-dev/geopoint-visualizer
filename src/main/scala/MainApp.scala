import AppRunner.run
import com.monovore.decline.CommandApp

object MainApp
    extends CommandApp(
      name = "geojson-formatter",
      header = "Plots given points on a map",
      main = {
        Cli.command.map { args =>
          val argsList = List(
            args.swap,
            args.inputOne,
            args.inputTwo,
            args.inputThree,
            args.inputFour,
            args.inputFive
          )

          run(argsList)
        }
      }
    )
