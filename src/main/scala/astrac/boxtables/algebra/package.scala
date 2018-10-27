package astrac.boxtables

import cats.data.Reader

package object algebra {
  type Rows[Primitive, A] = Reader[RowsConfig[Primitive], A]
  type Template[Primitive] = Reader[TableConfig[Primitive], List[Primitive]]
}
