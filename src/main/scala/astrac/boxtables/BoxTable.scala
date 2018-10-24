package astrac.boxtables

import cats.Foldable

object BoxTable {

  def makeString[F[_]: Foldable, A](data: F[A],
                                    sizing: Sizing,
                                    theme: Theme[String] = Themes.simple)(
      implicit algebra: TableAlgebra[A, String]
  ): String =
    algebra.table(data).run(TableConfig(theme, sizing)) mkString ("\n")
}
