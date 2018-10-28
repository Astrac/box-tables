package astrac.boxtables
package algebra

import cats.Contravariant

trait Cell[Primitive, Model] {
  def content(a: Model): Primitive
  def formatter: Formatter[Primitive]

  def format(f: Formatter[Primitive]): Cell[Primitive, Model] =
    Cell.instance(content, f)
}

object Cell {

  def apply[Primitive, Model](
      implicit c: Cell[Primitive, Model]): Cell[Primitive, Model] = c

  def instance[Primitive, Model](
      f: Model => Primitive,
      fmt: Formatter[Primitive]): Cell[Primitive, Model] =
    new Cell[Primitive, Model] {
      override def content(a: Model) = f(a)
      override lazy val formatter = fmt
    }

  implicit def contravariant[Primitive]: Contravariant[Cell[Primitive, ?]] =
    new Contravariant[Cell[Primitive, ?]] {
      override def contramap[A, B](fa: Cell[Primitive, A])(
          f: B => A): Cell[Primitive, B] = {
        Cell.instance(b => fa.content(f(b)), fa.formatter)
      }
    }
}
