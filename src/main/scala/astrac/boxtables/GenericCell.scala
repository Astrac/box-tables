package astrac.boxtables

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
}
