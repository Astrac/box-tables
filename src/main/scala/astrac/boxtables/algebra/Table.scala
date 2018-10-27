package astrac.boxtables
package algebra

import cats.Foldable
import cats.instances.list._
import cats.kernel.Monoid
import cats.syntax.monoid._
import cats.syntax.foldable._
import cats.syntax.list._
import cats.syntax.traverse._

trait Table[Primitive, Model] extends Line[Primitive, Model] {

  lazy val topMargin: Rows[Primitive, List[Primitive]] = theme.flatMap { t =>
    List.fill(t.margins.space.t)(marginLine(t.margins.fill.t)).sequence
  }

  lazy val topBorder: Rows[Primitive, List[Primitive]] = theme.flatMap { t =>
    borderLine(t.borders.t, t.corners.tl, t.intersections.t, t.corners.tr)
      .map(List(_))
  }

  lazy val rowsDivider: Rows[Primitive, List[Primitive]] = theme.flatMap { t =>
    t.dividers.h.fold[Rows[Primitive, List[Primitive]]](Rows.pure(Nil)) { h =>
      borderLine(h, t.intersections.l, t.intersections.c, t.intersections.r)
        .map(List(_))
    }
  }

  lazy val bottomBorder: Rows[Primitive, List[Primitive]] = theme.flatMap { t =>
    borderLine(t.borders.b, t.corners.bl, t.intersections.b, t.corners.br)
      .map(List(_))
  }

  lazy val bottomMargin: Rows[Primitive, List[Primitive]] = theme.flatMap { t =>
    List.fill(t.margins.space.b)(marginLine(t.margins.fill.b)).sequence
  }

  lazy val paddingTop: Rows[Primitive, List[Primitive]] = theme.flatMap { t =>
    List.fill(t.padding.space.t)(paddingLine(t.padding.fill.t)).sequence
  }

  lazy val paddingBottom: Rows[Primitive, List[Primitive]] = theme.flatMap {
    t =>
      List.fill(t.padding.space.b)(paddingLine(t.padding.fill.b)).sequence
  }

  def row(model: Model): Rows[Primitive, List[Primitive]] = {
    val contents = Model.toRow(model)

    val contentLines = contents.zipWithIndex
      .traverse {
        case (c, i) => cellWidth(i).map(F(_)(c))
      }
      .flatMap(transpose)
      .flatMap(_.traverse(contentLine))

    paddingTop |+| contentLines |+| paddingBottom
  }

  lazy val tableStart = topMargin |+| topBorder

  def rows[F[_]: Foldable](as: F[Model]): Rows[Primitive, List[Primitive]] =
    theme.flatMap { t =>
      as.foldMap(model => row(model) |+| rowsDivider)
        .map(_.toNel
          .fold(List.empty[Primitive])(l =>
            t.dividers.h.fold(l.toList)(_ => l.init)))
    }

  lazy val tableEnd = bottomBorder |+| bottomMargin

  def table[F[_]: Foldable](as: F[Model]): Rows[Primitive, List[Primitive]] =
    tableStart |+| rows(as) |+| tableEnd
}

object Table {
  implicit def instance[Primitive, Model](
      implicit monoid: Monoid[Primitive],
      formatter: Formatter[Primitive],
      modelRow: Row[Model]): Table[Primitive, Model] =
    new Table[Primitive, Model] {
      implicit val Primitive = monoid
      implicit val F = formatter
      implicit val Model = modelRow
    }

  def apply[Model, Primitive](
      implicit algebra: Table[Model, Primitive]): Table[Model, Primitive] =
    algebra
}
