package astrac.boxtables

package object string {
  type Cell[A] = GenericCell[String, A]
  type AutoRow[A] = GenericAutoRow[String, A]
  type Row[A] = GenericRow[String, A]
  type Sizing = astrac.boxtables.Sizing
  val Sizing = astrac.boxtables.Sizing

  val fullAuto = new Instances with GenericAutoRow.Instances {}
  val instances = new Instances {}
  val primitives = new Primitives {}
  val containers = new Containers {}
  val temporal = new Temporal {}
}
