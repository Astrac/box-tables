package astrac.boxtables

import cats.Foldable
import cats.instances.string._

object BoxTable {

  def makeString[F[_]: Foldable, A: Row](
      data: F[A],
      sizing: Sizing,
      theme: Theme[String] = Themes.simple): String =
    algebra[A](sizing, theme).table(data).mkString("\n")

  def algebra[A: Row](
      sizing: Sizing,
      theme: Theme[String] = Themes.simple): TableAlgebra[A, String] =
    new TableAlgebra(theme, sizing)
}
