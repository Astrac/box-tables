package astrac.boxtables

trait Formatter[Primitive] {
  def space: Primitive
  def apply(s: String)(w: Int): List[Primitive]
}

object Formatter {

  val basic = new Formatter[String] {
    override val space = " "
    override def apply(s: String)(w: Int): List[String] =
      if (w == 0) Nil
      else
        s.split("\n").toList.flatMap(_.grouped(w).toList.map(_.padTo(w, ' ')))
  }

}
