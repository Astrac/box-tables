package astrac.boxtables
package algebra

import cats.data.Reader

object Rows {
  def sizing[Primitive]: Rows[Primitive, Sizing] =
    Reader(_.sizing)

  def theme[Primitive]: Rows[Primitive, Theme[Primitive]] =
    Reader(_.theme)

  def pure[Primitive, A](t: A): Rows[Primitive, A] =
    Reader(_ => t)

  def apply[Primitive, A](f: RowsConfig[Primitive] => A): Rows[Primitive, A] =
    Reader(f)
}
