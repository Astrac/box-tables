package astrac.boxtables

import cats.ContravariantMonoidal
import shapeless.{Sized, Nat}
import shapeless.ops.nat.ToInt

sealed trait Row[Model] {
  def size: Int
  def toRow(in: Model): List[String]
}

object Row {

  def apply[Model: Row]: Row[Model] = implicitly

  def instance[Model, Size <: Nat](f: Model => Sized[List[String], Size])(
      implicit toInt: ToInt[Size]): Row[Model] =
    new Row[Model] {
      override val size = toInt()
      override def toRow(in: Model) = f(in)
    }

  private[boxtables] def unsafe[Model](s: Int,
                                       f: Model => List[String]): Row[Model] =
    new Row[Model] {
      override val size = s
      override def toRow(in: Model) = f(in)
    }

  def cell[Model: Cell]: Row[Model] =
    Row.instance(a => Sized[List](Cell[Model].content(a)))

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
