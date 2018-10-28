package astrac.boxtables
package string

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

  def instance[Model](cs: List[Cell[Model]]): Row[Model] =
    GenericRow.instance(cs)
}
