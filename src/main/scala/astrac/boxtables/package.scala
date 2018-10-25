package astrac

import cats.data.Reader

package object boxtables {
  type Rows[T, R] = Reader[RowsConfig[T], R]
  type Template[T] = Reader[TableConfig[T], List[T]]
}
