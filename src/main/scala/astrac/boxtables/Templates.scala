package astrac.boxtables

import cats.Foldable
import cats.instances.list._
import cats.syntax.monoid._

object Templates {
  private def header[T, H](header: H)(
      implicit algebra: TableAlgebra[T, H]): Rows[T, List[T]] =
    algebra.tableStart |+|
      algebra.row(header) |+|
      algebra.rowsDivider

  private def footer[T, F](footer: F)(
      implicit algebra: TableAlgebra[T, F]
  ): Rows[T, List[T]] =
    algebra.rowsDivider |+|
      algebra.row(footer) |+|
      algebra.tableEnd

  def simple[F[_]: Foldable, T, R](as: F[R])(
      implicit algebra: TableAlgebra[T, R]): Template[T] =
    (algebra.tableStart |+| algebra.rows(as) |+| algebra.tableEnd).local(_.main)

  def withHeader[F[_]: Foldable, T, H, R](h: H, as: F[R])(
      implicit hAlgebra: TableAlgebra[T, H],
      rAlgebra: TableAlgebra[T, R]
  ): Template[T] =
    header(h).local[TableConfig[T]](_.safeHeader) |+|
      rAlgebra.rows(as).local(_.main) |+|
      rAlgebra.tableEnd.local(_.main)

  def withFooter[F[_]: Foldable, T, R, E](as: F[R], f: E)(
      implicit rAlgebra: TableAlgebra[T, R],
      fAlgebra: TableAlgebra[T, E]
  ): Template[T] =
    rAlgebra.tableStart.local[TableConfig[T]](_.main) |+|
      rAlgebra.rows(as).local(_.main) |+|
      footer(f).local(_.safeFooter)

  def withHeaderAndFooter[F[_]: Foldable, T, H, R, E](h: H, as: F[R], f: E)(
      implicit hAlgebra: TableAlgebra[T, H],
      rAlgebra: TableAlgebra[T, R],
      fAlgebra: TableAlgebra[T, E]
  ): Template[T] =
    header(h).local[TableConfig[T]](_.safeHeader) |+|
      rAlgebra.rows(as).local(_.main) |+|
      footer(f).local(_.safeFooter)
}
