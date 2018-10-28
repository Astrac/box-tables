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
        length <- Gen.choose(cellWidth / 4, cellWidth * 3 / 2)
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

  property("Invariants") = checkFormatters(Formatter.basic,
                                           Formatter.leftAlign,
                                           Formatter.centerAlign,
                                           Formatter.rightAlign) { (w, _, ls) =>
    // println(w)
    // println(in)
    // println("*" * 80)
    // ls.foreach(println)
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
          .fold(true)(_.forall(x => x._1.trim.size + x._2.trim.size + 1 > w))))

  }

  property("LeftAlign.Invariants") = checkFormatters(Formatter.leftAlign) {
    (_, _, ls) =>
      ("Do not start with whitespace" |: ls.forall(!_.startsWith(" ")))
  }

  property("RightAlign.Invariants") = checkFormatters(Formatter.rightAlign) {
    (_, _, ls) =>
      ("Do not endwith whitespace" |: ls.forall(!_.endsWith(" ")))
  }

  property("CenterAlign.Invariants") = checkFormatters(Formatter.centerAlign) {
    (_, _, ls) =>
      (
        "Has whitespaces distributed evenly on sides" |:
          ls.forall { l =>
          val ls = l.takeWhile(_ == ' ').size
          val rs = l.reverse.takeWhile(_ == ' ').size
          ls >= rs - 1 && ls <= rs + 1
        }
      )
  }

  property("WithWordBoundaries.Example") = {
    Formatter.wordWrap(Examples.loremIpsum)(60).map(_.trim) ==
      List(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "Nullam id molestie erat. Duis auctor vestibulum lacus quis",
        "ultrices. Vestibulum et dapibus ligula. Fusce lacinia nisl",
        "id tincidunt egestas."
      )

    Formatter.wordWrap(Examples.loremIpsum)(30).map(_.trim) ==
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
