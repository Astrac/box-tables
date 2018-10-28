package astrac.boxtables

import cats.Contravariant

trait GenericCell[Primitive, Model] {
  def content(a: Model): Primitive
  def formatter: GenericFormatter[Primitive]

  def format(f: GenericFormatter[Primitive]): GenericCell[Primitive, Model] =
    GenericCell.instance(content, f)
}

object GenericCell {

  def apply[Primitive, Model](implicit c: GenericCell[Primitive, Model])
    : GenericCell[Primitive, Model] = c

  def instance[Primitive, Model](
      f: Model => Primitive,
      fmt: GenericFormatter[Primitive]): GenericCell[Primitive, Model] =
    new GenericCell[Primitive, Model] {
      override def content(a: Model) = f(a)
      override lazy val formatter = fmt
    }

  implicit def contravariant[Primitive]
    : Contravariant[GenericCell[Primitive, ?]] =
    new Contravariant[GenericCell[Primitive, ?]] {
      override def contramap[A, B](fa: GenericCell[Primitive, A])(
          f: B => A): GenericCell[Primitive, B] =
        GenericCell.instance(b => fa.content(f(b)), fa.formatter)
    }
}
