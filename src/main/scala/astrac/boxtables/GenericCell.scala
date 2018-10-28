package astrac.boxtables

import cats.Contravariant

trait GenericCell[Primitive, Model] {
  def content(a: Model): Primitive
}

object GenericCell {

  def apply[Primitive, Model](implicit c: GenericCell[Primitive, Model])
    : GenericCell[Primitive, Model] = c

  def instance[Primitive, Model](
      f: Model => Primitive): GenericCell[Primitive, Model] =
    new GenericCell[Primitive, Model] {
      def content(a: Model) = f(a)
    }

  implicit def contravariant[Primitive]
    : Contravariant[GenericCell[Primitive, ?]] =
    new Contravariant[GenericCell[Primitive, ?]] {
      override def contramap[A, B](fa: GenericCell[Primitive, A])(
          f: B => A): GenericCell[Primitive, B] =
        GenericCell.instance(b => fa.content(f(b)))
    }
}
