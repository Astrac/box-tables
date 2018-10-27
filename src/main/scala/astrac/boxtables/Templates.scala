package astrac.boxtables

import cats.Foldable
import cats.instances.list._
import cats.syntax.monoid._

object Templates {
  private def header[Primitive, Header](header: Header)(
      implicit algebra: TableAlgebra[Primitive, Header])
    : Rows[Primitive, List[Primitive]] =
    algebra.tableStart |+|
      algebra.row(header) |+|
      algebra.rowsDivider

  private def footer[Primitive, Footer](footer: Footer)(
      implicit algebra: TableAlgebra[Primitive, Footer]
  ): Rows[Primitive, List[Primitive]] =
    algebra.rowsDivider |+|
      algebra.row(footer) |+|
      algebra.tableEnd

  def simple[F[_]: Foldable, Primitive, Model](as: F[Model])(
      implicit algebra: TableAlgebra[Primitive, Model]): Template[Primitive] =
    (algebra.tableStart |+| algebra.rows(as) |+| algebra.tableEnd).local(_.main)

  def withHeader[F[_]: Foldable, Primitive, Header, Model](h: Header,
                                                           as: F[Model])(
      implicit hAlgebra: TableAlgebra[Primitive, Header],
      rAlgebra: TableAlgebra[Primitive, Model]
  ): Template[Primitive] =
    header(h).local[TableConfig[Primitive]](_.safeHeader) |+|
      rAlgebra.rows(as).local(_.main) |+|
      rAlgebra.tableEnd.local(_.main)

  def withFooter[F[_]: Foldable, Primitive, Model, Footer](as: F[Model],
                                                           f: Footer)(
      implicit rAlgebra: TableAlgebra[Primitive, Model],
      fAlgebra: TableAlgebra[Primitive, Footer]
  ): Template[Primitive] =
    rAlgebra.tableStart.local[TableConfig[Primitive]](_.main) |+|
      rAlgebra.rows(as).local(_.main) |+|
      footer(f).local(_.safeFooter)

  def withHeaderAndFooter[F[_]: Foldable, Primitive, Header, Model, Footer](
      h: Header,
      as: F[Model],
      f: Footer)(
      implicit hAlgebra: TableAlgebra[Primitive, Header],
      rAlgebra: TableAlgebra[Primitive, Model],
      fAlgebra: TableAlgebra[Primitive, Footer]
  ): Template[Primitive] =
    header(h).local[TableConfig[Primitive]](_.safeHeader) |+|
      rAlgebra.rows(as).local(_.main) |+|
      footer(f).local(_.safeFooter)
}
