package astrac.boxtables

import cats.Foldable
import cats.instances.list._
import cats.kernel.Monoid
import cats.syntax.monoid._
import cats.syntax.foldable._
import cats.syntax.list._
import cats.syntax.traverse._

trait TableAlgebra[A, T] extends LineAlgebra[A, T] {

  lazy val topMargin: Result[T, List[T]] = theme.flatMap { t =>
    List.fill(t.margins.space.t)(marginLine(t.margins.fill.t)).sequence
  }

  lazy val topBorder: Result[T, List[T]] = theme.flatMap { t =>
    borderLine(t.borders.t, t.corners.tl, t.intersections.t, t.corners.tr)
      .map(List(_))
  }

  lazy val rowsDivider: Result[T, List[T]] = theme.flatMap { t =>
    borderLine(t.dividers.h,
               t.intersections.l,
               t.intersections.c,
               t.intersections.r).map(List(_))
  }

  lazy val bottomBorder: Result[T, List[T]] = theme.flatMap { t =>
    borderLine(t.borders.b, t.corners.bl, t.intersections.b, t.corners.br)
      .map(List(_))
  }

  lazy val bottomMargin: Result[T, List[T]] = theme.flatMap { t =>
    List.fill(t.margins.space.b)(marginLine(t.margins.fill.b)).sequence
  }

  lazy val paddingTop: Result[T, List[T]] = theme.flatMap { t =>
    List.fill(t.padding.space.t)(paddingLine(t.padding.fill.t)).sequence
  }

  lazy val paddingBottom: Result[T, List[T]] = theme.flatMap { t =>
    List.fill(t.padding.space.b)(paddingLine(t.padding.fill.b)).sequence
  }

  def row(a: A): Result[T, List[T]] = {
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

  def rows[F[_]: Foldable](as: F[A]): Result[T, List[T]] =
    as.foldMap(a => row(a) |+| rowsDivider)
      .map(_.toNel
        .fold(List.empty[T])(_.init))

  lazy val tableEnd = bottomBorder |+| bottomMargin

  def table[F[_]: Foldable](as: F[A]): Result[T, List[T]] =
    tableStart |+| rows(as) |+| tableEnd
}

object TableAlgebra {
  implicit def instance[A, T](implicit monoid: Monoid[T],
                              formatter: Formatter[T],
                              aRow: Row[A]): TableAlgebra[A, T] =
    new TableAlgebra[A, T] {
      implicit val T = monoid
      implicit val F = formatter
      implicit val R = aRow
    }

  def apply[A, T](implicit t: TableAlgebra[A, T]): TableAlgebra[A, T] = t
}
