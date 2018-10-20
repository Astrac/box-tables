package astrac.boxtables

trait Cell[A] {
  def content(a: A): String
}

object Cell {

  def apply[A: Cell]: Cell[A] = implicitly

  def instance[A](f: A => String): Cell[A] = new Cell[A] {
    def content(a: A) = f(a)
  }
}
