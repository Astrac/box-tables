package astrac.boxtables

import astrac.boxtables.instances.all._
import cats.instances.list._
import cats.syntax.contravariantSemigroupal._
import org.scalacheck.{Gen, Prop, Properties}

class BoxTableSpec extends Properties("BoxTable") {

  val exampleSingleLine =
    """
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↙>>
<<<<→vvvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv←>>
<<<<→>>>>foo    <<|>>>>12    <<|>>>>x     <<|>>>>l     <<←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<↦-------------↺------------↺------------↺------------↤>>
<<<<→vvvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv←>>
<<<<→>>>>bar    <<|>>>>4     <<|>>>>y     <<|>>>>l     <<←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↖>>
vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
"""

  val exampleMultiLine =
    """
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↙>>
<<<<→vvvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv←>>
<<<<→>>>>Lorem i<<|>>>>12    <<|>>>>x     <<|>>>>l     <<←>>
<<<<→>>>>psum   <<|>>>>      <<|>>>>      <<|>>>>      <<←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<↦-------------↺------------↺------------↺------------↤>>
<<<<→vvvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv|vvvvvvvvvvvv←>>
<<<<→>>>>bar    <<|>>>>4     <<|>>>>Lorem <<|>>>>l     <<←>>
<<<<→>>>>       <<|>>>>      <<|>>>>ipsum <<|>>>>      <<←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<→^^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^|^^^^^^^^^^^^←>>
<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↖>>
vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
"""

  val testTheme = Theme[String](
    borders = Sides(t = "↓", b = "↑", l = "→", r = "←"),
    corners = Corners(tl = "↘", tr = "↙", bl = "↗", br = "↖"),
    dividers = Dividers(v = "|", h = "-"),
    padding = Padding(space = Spacing(1, 2, 3, 4),
                      fill = Sides(t = "v", b = "^", l = ">", r = "<")),
    margins = Margins(space = Spacing(1, 2, 3, 4),
                      fill = Sides(b = "v", t = "^", r = ">", l = "<")),
    intersections = Intersections(l = "↦", r = "↤", t = "↧", b = "↥", c = "↺")
  )

  case class Foo(x: String, y: Int, z: String)

  implicit val fooRow: Row[Foo] =
    (Row.cell[String], Row.cell[Int], Row.cell[String], Row.cell[String])
      .contramapN[Foo](f => (f.x, f.y, f.z, "l"))

  val sizing = Sizing.Equal(60)

  property("TableSize") = Prop.forAllNoShrink(Gen.choose(35, 100)) { w: Int =>
    val algebra = BoxTable.algebra(Sizing.Equal(w), testTheme)
    val lines = algebra.table(List(Foo("foo", 12, "x"), Foo("bar", 4, "y")))

    lines.forall(_.size == w)
  }

  property("ThemedTableSingleLine") = {
    val table = BoxTable
      .makeString(List(Foo("foo", 12, "x"), Foo("bar", 4, "y")),
                  sizing,
                  testTheme)
      .trim()

    table == (exampleSingleLine.trim())
  }

  property("ThemedTableMultiLine") = {
    val data = List(Foo("Lorem ipsum", 12, "x"), Foo("bar", 4, "Lorem ipsum"))
    val table = BoxTable.makeString(data, sizing, testTheme).trim()

    table == (exampleMultiLine.trim())
  }
}
