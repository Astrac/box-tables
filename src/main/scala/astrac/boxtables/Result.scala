package astrac.boxtables

import cats.data.Reader

object Result {
  def sizing[T]: Result[T, Sizing] =
    Reader(_.sizing)

  def theme[T]: Result[T, Theme[T]] =
    Reader(_.theme)

  def pure[T, A](t: A): Result[T, A] =
    Reader(_ => t)

  def apply[T, A](f: TableConfig[T] => A): Result[T, A] =
    Reader(f)
}
