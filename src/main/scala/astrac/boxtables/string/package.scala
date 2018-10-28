package astrac.boxtables

package object string {
  type Cell[A] = algebra.Cell[String, A]
  type AutoRow[A] = algebra.AutoRow[String, A]
  type Row[A] = algebra.Row[String, A]
  type Formatter = algebra.Formatter[String]
  type Sizing = astrac.boxtables.Sizing
  val Sizing = astrac.boxtables.Sizing

  val fullAuto = new Instances with algebra.AutoRow.Instances {}
  val instances = new Instances {}
  val primitives = new Primitives {}
  val containers = new Containers {}
  val temporal = new Temporal {}
}
