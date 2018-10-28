package astrac.boxtables
package algebra

trait Formatter[Primitive] {
  def apply(s: Primitive)(w: Int): List[Primitive]
}

object Formatter {
  def instance[Primitive](
      f: (Primitive, Int) => List[Primitive]): Formatter[Primitive] =
    new Formatter[Primitive] {
      override def apply(s: Primitive)(w: Int) = f(s, w)
    }
}
