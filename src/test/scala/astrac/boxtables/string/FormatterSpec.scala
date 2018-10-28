package astrac.boxtables
package string

import cats.syntax.list._
import org.scalacheck.{Gen, Prop, Properties}
import org.scalacheck.Prop.BooleanOperators

class FormatterSpec extends Properties("Formatter") {

  def checkFormatters(fs: Formatter*)(
      invariant: (Int, String, List[String]) => Prop): Prop = {
    def wordGen(cellWidth: Int) =
      for {
        length <- Gen.choose(cellWidth / 4, cellWidth * 2)
        chars <- Gen.listOfN(length, Gen.alphaNumChar)
      } yield chars.mkString

    val generator = for {
      width <- Gen.choose(10, 100)
      wordsCount <- Gen.choose(5, 30)
      words <- Gen.listOfN(wordsCount, wordGen(width))
    } yield (width, words.mkString(" "))

    fs.foldLeft[Prop](true) { (prop, formatter) =>
      prop && Prop.forAllNoShrink(generator) {
        case (w, t) =>
          invariant(w, t, formatter(t)(w)) :| s"Formatter: $formatter"
      }
    }
  }

  property("Invariants") =
    checkFormatters(Formatter.basic, Formatter.withWordBoundaries) {
      (w, _, ls) =>
        ("Contain no newlines" |: ls.forall(!_.contains("\n"))) &&
        ("Contain no double-whitespaces except the padding" |:
          ls.forall(!_.trim.contains("  "))) &&
        ("Respects size constraints" |: ls.forall(_.size == w)) &&
        ("Not break lines unnecessarily" |:
          ls.toNel.fold(true)(
          ls =>
            ls.toList
              .zip(ls.tail)
              .toNel
              .fold(true)(_.forall(x =>
                x._1.trim.size + x._2.trim.size + 1 > w))))

    }

  property("WithWordBoundaries.Invariants") =
    checkFormatters(Formatter.withWordBoundaries) { (_, _, ls) =>
      ("Do not start with whitespace" |: ls.forall(!_.startsWith(" ")))
    }

  property("WithWordBoundaries.Example") = {
    Formatter.withWordBoundaries(Examples.loremIpsum)(60).map(_.trim) ==
      List(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "Nullam id molestie erat. Duis auctor vestibulum lacus quis",
        "ultrices. Vestibulum et dapibus ligula. Fusce lacinia nisl",
        "id tincidunt egestas."
      )

    Formatter.withWordBoundaries(Examples.loremIpsum)(30).map(_.trim) ==
      List(
        "Lorem ipsum dolor sit amet,",
        "consectetur adipiscing elit.",
        "Nullam id molestie erat. Duis",
        "auctor vestibulum lacus quis",
        "ultrices. Vestibulum et",
        "dapibus ligula. Fusce lacinia",
        "nisl id tincidunt egestas."
      )

  }
}
