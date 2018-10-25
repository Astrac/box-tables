package astrac.boxtables

import cats.Foldable

object StringTables {

  def simple[F[_]: Foldable, R](data: F[R],
                                sizing: Sizing,
                                theme: Theme[String])(
      implicit algebra: TableAlgebra[String, R]
  ): String =
    Templates
      .simple(data)
      .run(TableConfig(RowsConfig(theme, sizing)))
      .mkString("\n")

  def withHeader[F[_]: Foldable, H, R](h: H,
                                       as: F[R],
                                       tableConfig: TableConfig[String])(
      implicit hAlgebra: TableAlgebra[String, H],
      rAlgebra: TableAlgebra[String, R]
  ): String =
    Templates
      .withHeader(h, as)
      .run(tableConfig)
      .mkString("\n")

  def withFooter[F[_]: Foldable, R, E](as: F[R],
                                       f: E,
                                       tableConfig: TableConfig[String])(
      implicit rAlgebra: TableAlgebra[String, R],
      fAlgebra: TableAlgebra[String, E]
  ): String =
    Templates
      .withFooter(as, f)
      .run(tableConfig)
      .mkString("\n")

  def withHeaderAndFooter[F[_]: Foldable, H, R, E](
      h: H,
      as: F[R],
      f: E,
      tableConfig: TableConfig[String])(
      implicit hAlgebra: TableAlgebra[String, H],
      rAlgebra: TableAlgebra[String, R],
      fAlgebra: TableAlgebra[String, E]
  ): String =
    Templates
      .withHeaderAndFooter(h, as, f)
      .run(tableConfig)
      .mkString("\n")

  def markdown[F[_]: Foldable, H, R](h: H,
                                     as: F[R],
                                     sizing: Sizing = Sizing.Equal(80))(
      implicit hAlgebra: TableAlgebra[String, H],
      rAlgebra: TableAlgebra[String, R]): String =
    withHeader(
      h,
      as,
      TableConfig[String](main = RowsConfig(Themes.markdownMain, sizing),
                          header =
                            Some(RowsConfig(Themes.markdownHeader, sizing))))
}
