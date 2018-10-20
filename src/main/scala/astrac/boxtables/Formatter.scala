package astrac.boxtables

trait Formatter[T] {
  def space: T
  def apply(w: Int)(s: String): List[T]
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
