package astrac.boxtables

import astrac.boxtables.instances.all._
import cats.instances.list._
import cats.instances.string._
import cats.syntax.contravariantSemigroupal._
import org.scalacheck.{Gen, Prop}
import scala.io.Source
import org.scalacheck.Properties
import shapeless.Nat

class StringTablesSpec extends Properties("StringTables") {
  case class Counters(visits: Int, transfers: Int)
  case class User(name: String, age: Int, active: Boolean, counters: Counters)

  implicit val permissionCell: Cell[Counters] =
    Cell.instance(p => s"Visits: ${p.visits}\nTransfers: ${p.transfers}")

  implicit val userRow: Row[User] = Generic[User].derive

  val testTheme = Theme[String](
    borders = Sides(t = "↓", b = "↑", l = "→", r = "←"),
    corners = Corners(tl = "↘", tr = "↙", bl = "↗", br = "↖"),
    dividers = Dividers.hv(v = "|", h = "-"),
    padding = Padding(space = Spacing(1, 2, 3, 4),
                      fill = Sides(t = "v", b = "^", l = ">", r = "<")),
    margins = Margins(space = Spacing(1, 2, 3, 4),
                      fill = Sides(b = "v", t = "^", r = ">", l = "<")),
    intersections = Intersections(l = "↦", r = "↤", t = "↧", b = "↥", c = "↺")
  )

  // Expects data, empty line and list of examples for that data
  // separated by blank lines
  def readExample(name: String): (List[User], List[String]) = {
    val fileStream = getClass.getResourceAsStream(name)
    val lines = Source.fromInputStream(fileStream).getLines.toList
    val data = lines.takeWhile(_.trim.nonEmpty).map {
      _.split(",").map(_.trim()).toList match {
        case List(name, age, active, cVisits, cTransfers) =>
          User(name,
               age.toInt,
               active.toBoolean,
               Counters(cVisits.toInt, cTransfers.toInt))
      }
    }

    val tables = lines
      .dropWhile(_.trim.nonEmpty)
      .mkString("\n")
      .split("\n\n")
      .map(_.trim())
      .toList

    (data, tables)
  }

  property("TestThemeEqualSizing") = {
    val (data, List(t45, t80, t120)) =
      readExample("/test-theme-equal-sizing.example")

    def makeTable(size: Int) =
      StringTables.simple(data, Sizing.Equal(size), testTheme)

    makeTable(45) == t45 && makeTable(80) == t80 && makeTable(120) == t120
  }

  property("TestThemeWeightedSizing") = {
    val weights = List(3, 1, 1, 4)

    val (data, List(t45, t80, t120)) =
      readExample("/test-theme-weighted-sizing.example")

    def makeTable(size: Int) =
      StringTables.simple(data, Sizing.Weighted(size, weights), testTheme)

    makeTable(45) == t45 && makeTable(80) == t80 && makeTable(120) == t120
  }

  property("TestThemeFixedSizing") = {
    val (data, List(t1, t2)) = readExample("/test-theme-fixed-sizing.example")

    val sizes1 = List(20, 3, 3, 30)
    val sizes2 = List(15, 2, 5, 20)

    def makeTable(sizes: List[Int]) =
      StringTables.simple(data, Sizing.Fixed(sizes), testTheme)

    makeTable(sizes1) == t1 && makeTable(sizes2) == t2
  }

  property("Markdown") = {
    val (data, List(t)) =
      readExample("/markdown.example")

    implicit val headerRow = Generic.tupleN[Nat._3, String].derive

    implicit val userRow: Row[User] =
      (Row.cell[String], Row.cell[Int], Row.cell[Boolean]).contramapN[User](u =>
        (u.name, u.age, u.active))

    StringTables
      .markdown(("Name", "Age", "Active"), data)
      .trim() == t
  }

  property("TableSizeMustBeRespected") =
    Prop.forAllNoShrink(Gen.choose(35, 100)) { w: Int =>
      val algebra = TableAlgebra[String, User]

      val lines = algebra
        .table(
          List(
            User("Kilgore Trout", 30, true, Counters(18, 35)),
            User("Billy Pilgrim", 20, false, Counters(5, 7)),
            User("Mandarax", 3, true, Counters(10, 0))
          ))
        .run(RowsConfig(testTheme, Sizing.Equal(w)))

      lines.forall(_.size == w)
    }
}