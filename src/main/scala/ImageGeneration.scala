import Models.FilePath
import cats.MonadError
import sttp.client3.{SttpBackend, UriContext, asByteArray, basicRequest}

import java.awt.Desktop
import java.net.URI
import java.nio.file.{Files, Paths}
import cats.implicits.{catsSyntaxTuple2Semigroupal, toFlatMapOps}

object ImageGeneration {
  def downloadImage[F[_], P](
      outputPath: String,
      uri: URI,
      backend: SttpBackend[F, P]
  )(implicit
      ME: MonadError[F, Throwable]
  ): F[Unit] = {
    val request = basicRequest.get(uri"${uri.toString}").response(asByteArray)

    request.send(backend).flatMap { response =>
      response.body match {
        case Left(message) =>
          ME.raiseError(new Exception(s"Failed to download image: $message"))
        case Right(byteArray) =>
          ME.catchNonFatal(Files.write(Paths.get(outputPath), byteArray))
      }
    }
  }
}
