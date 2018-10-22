package astrac.boxtables

import cats.ContravariantMonoidal
import shapeless.{Sized, Nat}
import shapeless.ops.nat.ToInt

sealed trait Row[A] {
  def size: Int
  def toRow(in: A): List[String]
}

object Row {

  def apply[A: Row]: Row[A] = implicitly

  def instance[A, N <: Nat](f: A => Sized[List[String], N])(
      implicit toInt: ToInt[N]): Row[A] =
    new Row[A] {
      override val size = toInt()
      override def toRow(in: A) = f(in)
    }

  private[boxtables] def unsafe[A](s: Int, f: A => List[String]): Row[A] =
    new Row[A] {
      override val size = s
      override def toRow(in: A) = f(in)
    }

  def cell[A: Cell]: Row[A] =
    Row.instance(a => Sized[List](Cell[A].content(a)))

  implicit val contravariantMonoidal: ContravariantMonoidal[Row] =
    new ContravariantMonoidal[Row] {
      def unit: Row[Unit] = Row.instance(_ => Sized[List]())

      def contramap[A, B](fa: Row[A])(f: B => A): Row[B] =
        Row.unsafe(fa.size, b => fa.toRow(f(b)))

      def product[A, B](fa: Row[A], fb: Row[B]): Row[(A, B)] =
        Row.unsafe(fa.size + fb.size, {
          case (a, b) => fa.toRow(a) ++ fb.toRow(b)
        })
    }

}
