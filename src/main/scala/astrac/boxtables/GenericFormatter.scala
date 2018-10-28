package astrac.boxtables

trait GenericFormatter[Primitive] {
  def space: Primitive
  def apply(s: Primitive)(w: Int): List[Primitive]
}
