package astrac

import cats.data.Reader

package object boxtables {
  type Rows[Primitive, A] = Reader[RowsConfig[Primitive], A]
  type Template[Primitive] = Reader[TableConfig[Primitive], List[Primitive]]
  def discard[T]: T => Unit = _ => ()
}
