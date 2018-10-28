package astrac.boxtables
package algebra

import cats.ContravariantMonoidal
import cats.syntax.contravariant._

sealed trait Row[Primitive, Model] {
  def cells: List[Cell[Primitive, Model]]
  lazy val columns: Int = cells.size

  def format(f: Formatter[Primitive]): Row[Primitive, Model] =
    Row.instance(cells.map(_.format(f)))

  def formatByIndex(f: Int => Formatter[Primitive]): Row[Primitive, Model] =
    Row.instance(cells.zipWithIndex.map(ci => ci._1.format(f(ci._2))))
}

object Row {

  def apply[Primitive, Model](
      implicit r: Row[Primitive, Model]): Row[Primitive, Model] =
    r

  def instance[Primitive, Model](
      cs: List[Cell[Primitive, Model]]): Row[Primitive, Model] =
    new Row[Primitive, Model] {
      override val cells = cs
    }

  implicit def cellInstance[Primitive, Model](
      implicit c: Cell[Primitive, Model]): Row[Primitive, Model] =
    Row.instance(List(Cell[Primitive, Model]))

  implicit def contravariantMonoidal[Primitive]
    : ContravariantMonoidal[Row[Primitive, ?]] =
    new ContravariantMonoidal[Row[Primitive, ?]] {
      def unit: Row[Primitive, Unit] =
        Row.instance(Nil)

      def contramap[A, B](fa: Row[Primitive, A])(f: B => A): Row[Primitive, B] =
        Row.instance(fa.cells.map(_.contramap(f)))

      def product[A, B](fa: Row[Primitive, A],
                        fb: Row[Primitive, B]): Row[Primitive, (A, B)] =
        Row.instance(
          fa.cells.map(_.contramap[(A, B)](_._1)) ++ fb.cells.map(
            _.contramap[(A, B)](_._2)))
    }

}
