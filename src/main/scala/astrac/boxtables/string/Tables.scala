package astrac.boxtables
package string

import cats.Foldable
import astrac.boxtables.algebra.{Table, Templates}

object Tables {

  def simple[F[_]: Foldable, Model](data: F[Model],
                                    sizing: Sizing,
                                    theme: Theme[String])(
      implicit algebra: Table[String, Model]
  ): String =
    Templates
      .simple(data)
      .run(TableConfig(RowsConfig(theme, sizing)))
      .mkString("\n")

  def withHeader[F[_]: Foldable, Header, Model](
      h: Header,
      as: F[Model],
      tableConfig: TableConfig[String])(
      implicit hAlgebra: Table[String, Header],
      rAlgebra: Table[String, Model]
  ): String =
    Templates
      .withHeader(h, as)
      .run(tableConfig)
      .mkString("\n")

  def withFooter[F[_]: Foldable, Model, Footer](
      as: F[Model],
      f: Footer,
      tableConfig: TableConfig[String])(
      implicit rAlgebra: Table[String, Model],
      fAlgebra: Table[String, Footer]
  ): String =
    Templates
      .withFooter(as, f)
      .run(tableConfig)
      .mkString("\n")

  def withHeaderAndFooter[F[_]: Foldable, Header, Model, Footer](
      h: Header,
      as: F[Model],
      f: Footer,
      tableConfig: TableConfig[String])(
      implicit hAlgebra: Table[String, Header],
      rAlgebra: Table[String, Model],
      fAlgebra: Table[String, Footer]
  ): String =
    Templates
      .withHeaderAndFooter(h, as, f)
      .run(tableConfig)
      .mkString("\n")

  def markdown[F[_]: Foldable, Header, Model](
      h: Header,
      as: F[Model],
      sizing: Sizing = Sizing.Equal(80))(
      implicit hAlgebra: Table[String, Header],
      rAlgebra: Table[String, Model]): String =
    withHeader(
      h,
      as,
      TableConfig[String](main = RowsConfig(Themes.markdownMain, sizing),
                          header =
                            Some(RowsConfig(Themes.markdownHeader, sizing))))
}
