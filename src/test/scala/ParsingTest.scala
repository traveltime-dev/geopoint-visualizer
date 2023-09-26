import Implicits.runtime
import Models.{CoordinatesList, FilePath, Point}
import Parsing._
import cats.effect.IO
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.ByteArrayInputStream
import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

class ParsingTest extends AnyFlatSpec with Matchers {
  def mockBufferedSource(content: String): BufferedSource = {
    val stream = new ByteArrayInputStream(content.getBytes)
    Source.fromInputStream(stream)
  }

  "readFile" should "successfully read an existing file" in {
    val existingFilePath =
      FilePath("inputDir/input.json")

    val result: Try[BufferedSource] =
      Try(readFile[IO](existingFilePath).unsafeRunSync())

    result shouldBe a[Success[_]]
  }

  it should "fail when file doesn't exist" in {
    val nonExistingFilePath =
      FilePath("non_existing_file_path.json")

    val result: Try[BufferedSource] =
      Try(readFile[IO](nonExistingFilePath).unsafeRunSync())

    result shouldBe a[Failure[_]]
    result.failed.get.getMessage should include(
      "Failed to read file: non_existing_file_path.json"
    )
  }

  "parseInputCoordinates" should "successfully parse and optionally swap coordinates" in {
    val jsonSource = mockBufferedSource("""[[
                                          |    [
                                          |      1,
                                          |      2
                                          |    ],
                                          |    [
                                          |      3,
                                          |      4
                                          |    ]
                                          |  ]]""".stripMargin)
    val expectedCoordinates =
      List(CoordinatesList(List(Point(2, 1), Point(4, 3))))

    val result = parseInputCoordinates(jsonSource, swap = true)

    result shouldEqual Some(expectedCoordinates)
  }

  it should "return None for malformed JSON" in {
    val jsonSource =
      mockBufferedSource("""not a json""")

    val result = parseInputCoordinates(jsonSource, swap = false)

    result shouldEqual None
  }

  it should "return parsed coordinates without swap when swap = false" in {
    val jsonSource = mockBufferedSource("""[[
        |    [
        |      1,
        |      2
        |    ],
        |    [
        |      3,
        |      4
        |    ]
        |  ]]""".stripMargin)
    val expectedCoordinates =
      List(CoordinatesList(List(Point(1, 2), Point(3, 4))))

    val result = parseInputCoordinates(jsonSource, swap = false)

    result shouldEqual Some(expectedCoordinates)
  }
}
