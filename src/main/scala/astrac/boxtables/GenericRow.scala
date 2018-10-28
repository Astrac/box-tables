package astrac.boxtables

import cats.ContravariantMonoidal
import cats.syntax.contravariant._

sealed trait GenericRow[Primitive, Model] {
  def cells: List[GenericCell[Primitive, Model]]
  def contents(model: Model): List[Primitive] = cells.map(_.content(model))
  lazy val columns: Int = cells.size
}

object GenericRow {

  def apply[Primitive, Model](
      implicit r: GenericRow[Primitive, Model]): GenericRow[Primitive, Model] =
    r

  def instance[Primitive, Model](
      cs: List[GenericCell[Primitive, Model]]): GenericRow[Primitive, Model] =
    new GenericRow[Primitive, Model] {
      override val cells = cs
    }

  implicit def cellInstance[Primitive, Model](
      implicit c: GenericCell[Primitive, Model]): GenericRow[Primitive, Model] =
    GenericRow.instance(List(GenericCell[Primitive, Model]))

  implicit def contravariantMonoidal[Primitive]
    : ContravariantMonoidal[GenericRow[Primitive, ?]] =
    new ContravariantMonoidal[GenericRow[Primitive, ?]] {
      def unit: GenericRow[Primitive, Unit] =
        GenericRow.instance(Nil)

      def contramap[A, B](fa: GenericRow[Primitive, A])(
          f: B => A): GenericRow[Primitive, B] =
        GenericRow.instance(fa.cells.map(_.contramap(f)))

      def product[A, B](
          fa: GenericRow[Primitive, A],
          fb: GenericRow[Primitive, B]): GenericRow[Primitive, (A, B)] =
        GenericRow.instance(
          fa.cells.map(_.contramap[(A, B)](_._1)) ++
            fb.cells.map(_.contramap[(A, B)](_._2)))
    }

}
