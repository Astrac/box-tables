package astrac.boxtables
package algebra

trait Formatter[Primitive] {
  def apply(s: Primitive)(w: Int): List[Primitive]
}
