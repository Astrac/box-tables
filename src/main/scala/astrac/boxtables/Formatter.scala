package astrac.boxtables

trait Formatter[Primitive] {
  def space: Primitive
  def apply(w: Int)(s: String): List[Primitive]
}

object Formatter {

  implicit val stringFormatter = new Formatter[String] {
    override val space = " "
    override def apply(w: Int)(s: String): List[String] =
      if (w == 0) Nil
      else
        s.split("\n").toList.flatMap(_.grouped(w).toList.map(_.padTo(w, ' ')))
  }

}
