package astrac.boxtables

import cats.Foldable
import cats.instances.list._
import cats.kernel.Monoid
import cats.syntax.monoid._
import cats.syntax.foldable._
import cats.syntax.list._
import cats.syntax.traverse._

trait TableAlgebra[T, R] extends LineAlgebra[T, R] {

  lazy val topMargin: Rows[T, List[T]] = theme.flatMap { t =>
    List.fill(t.margins.space.t)(marginLine(t.margins.fill.t)).sequence
  }

  lazy val topBorder: Rows[T, List[T]] = theme.flatMap { t =>
    borderLine(t.borders.t, t.corners.tl, t.intersections.t, t.corners.tr)
      .map(List(_))
  }

  lazy val rowsDivider: Rows[T, List[T]] = theme.flatMap { t =>
    t.dividers.h.fold[Rows[T, List[T]]](Rows.pure(Nil)) { h =>
      borderLine(h, t.intersections.l, t.intersections.c, t.intersections.r)
        .map(List(_))
    }
  }

  lazy val bottomBorder: Rows[T, List[T]] = theme.flatMap { t =>
    borderLine(t.borders.b, t.corners.bl, t.intersections.b, t.corners.br)
      .map(List(_))
  }

  lazy val bottomMargin: Rows[T, List[T]] = theme.flatMap { t =>
    List.fill(t.margins.space.b)(marginLine(t.margins.fill.b)).sequence
  }

  lazy val paddingTop: Rows[T, List[T]] = theme.flatMap { t =>
    List.fill(t.padding.space.t)(paddingLine(t.padding.fill.t)).sequence
  }

  lazy val paddingBottom: Rows[T, List[T]] = theme.flatMap { t =>
    List.fill(t.padding.space.b)(paddingLine(t.padding.fill.b)).sequence
  }

  def row(a: R): Rows[T, List[T]] = {
    val contents = R.toRow(a)

    val contentLines = contents.zipWithIndex
      .traverse {
        case (c, i) => cellWidth(i).map(F(_)(c))
      }
      .flatMap(transpose)
      .flatMap(_.traverse(contentLine))

    paddingTop |+| contentLines |+| paddingBottom
  }

  lazy val tableStart = topMargin |+| topBorder

  def rows[F[_]: Foldable](as: F[R]): Rows[T, List[T]] =
    as.foldMap(a => row(a) |+| rowsDivider)
      .map(_.toNel
        .fold(List.empty[T])(_.init))

  lazy val tableEnd = bottomBorder |+| bottomMargin

  def table[F[_]: Foldable](as: F[R]): Rows[T, List[T]] =
    tableStart |+| rows(as) |+| tableEnd
}

object TableAlgebra {
  implicit def instance[T, R](implicit monoid: Monoid[T],
                              formatter: Formatter[T],
                              aRow: Row[R]): TableAlgebra[T, R] =
    new TableAlgebra[T, R] {
      implicit val T = monoid
      implicit val F = formatter
      implicit val R = aRow
    }

  def apply[R, T](implicit t: TableAlgebra[R, T]): TableAlgebra[R, T] = t
}
