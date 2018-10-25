package astrac.boxtables

import cats.data.Reader

object Rows {
  def sizing[T]: Rows[T, Sizing] =
    Reader(_.sizing)

  def theme[T]: Rows[T, Theme[T]] =
    Reader(_.theme)

  def pure[T, A](t: A): Rows[T, A] =
    Reader(_ => t)

  def apply[T, A](f: RowsConfig[T] => A): Rows[T, A] =
    Reader(f)
}
