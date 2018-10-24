package astrac

import cats.data.Reader

package object boxtables {
  type Result[T, A] = Reader[TableConfig[T], A]
}
