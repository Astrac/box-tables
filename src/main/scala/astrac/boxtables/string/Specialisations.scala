package astrac.boxtables
package string

import shapeless.ops.nat.ToInt
import shapeless.{Nat, Sized}

object Cell {
  def apply[Model: Cell]: Cell[Model] = implicitly
  def instance[Model](f: Model => String): Cell[Model] = GenericCell.instance(f)
}

object AutoRow {
  def apply[Model: AutoRow]: Row[Model] = implicitly[AutoRow[Model]].row
  def derive[Model: AutoRow]: Row[Model] = AutoRow[Model]
}

object Row {
  def apply[Model: Row]: Row[Model] = implicitly

  def instance[Model, Size <: Nat](f: Model => Sized[List[String], Size])(
      implicit toInt: ToInt[Size]): Row[Model] =
    GenericRow.instance(f)

  def cell[Model: Cell]: Row[Model] =
    GenericRow.cell
}
