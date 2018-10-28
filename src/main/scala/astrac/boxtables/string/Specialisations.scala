package astrac.boxtables
package string

object Cell {
  def apply[Model: Cell]: Cell[Model] = implicitly
  def instance[Model](f: Model => String,
                      formatter: Formatter = Formatter.basic): Cell[Model] =
    algebra.Cell.instance(f, formatter)
}

object AutoRow {
  def apply[Model: AutoRow]: Row[Model] = implicitly[AutoRow[Model]].row
  def derive[Model: AutoRow]: Row[Model] = AutoRow[Model]
  def formatted[Model: AutoRow](f: Formatter): Row[Model] =
    AutoRow[Model].format(f)
}

object Row {
  def apply[Model: Row]: Row[Model] = implicitly

  def instance[Model](cs: List[Cell[Model]]): Row[Model] =
    algebra.Row.instance(cs)
}
