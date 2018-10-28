package astrac.boxtables

import cats.ContravariantMonoidal
import shapeless.{Sized, Nat}
import shapeless.ops.nat.ToInt

sealed trait GenericRow[Primitive, Model] {
  def size: Int
  def toRow(in: Model): List[Primitive]
}

object GenericRow {

  def apply[Primitive, Model](
      implicit r: GenericRow[Primitive, Model]): GenericRow[Primitive, Model] =
    r

  def instance[Primitive, Model, Size <: Nat](
      f: Model => Sized[List[Primitive], Size])(
      implicit toInt: ToInt[Size]): GenericRow[Primitive, Model] =
    new GenericRow[Primitive, Model] {
      override val size = toInt()
      override def toRow(in: Model) = f(in)
    }

  private[boxtables] def unsafe[Primitive, Model](
      s: Int,
      f: Model => List[Primitive]): GenericRow[Primitive, Model] =
    new GenericRow[Primitive, Model] {
      override val size = s
      override def toRow(in: Model) = f(in)
    }

  implicit def cellInstance[Primitive, Model](
      implicit c: GenericCell[Primitive, Model]): GenericRow[Primitive, Model] =
    GenericRow.instance(a =>
      Sized[List](GenericCell[Primitive, Model].content(a)))

  implicit def contravariantMonoidal[Primitive]
    : ContravariantMonoidal[GenericRow[Primitive, ?]] =
    new ContravariantMonoidal[GenericRow[Primitive, ?]] {
      def unit: GenericRow[Primitive, Unit] =
        GenericRow.instance(_ => Sized[List]())

      def contramap[A, B](fa: GenericRow[Primitive, A])(
          f: B => A): GenericRow[Primitive, B] =
        GenericRow.unsafe(fa.size, b => fa.toRow(f(b)))

      def product[A, B](
          fa: GenericRow[Primitive, A],
          fb: GenericRow[Primitive, B]): GenericRow[Primitive, (A, B)] =
        GenericRow.unsafe(fa.size + fb.size, {
          case (a, b) => fa.toRow(a) ++ fb.toRow(b)
        })
    }

}
