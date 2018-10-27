package astrac.boxtables

trait Formatter[Primitive] {
  def space: Primitive
  def apply(s: String)(w: Int): List[Primitive]
}

object Formatter {

  val withWordBoundaries = new Formatter[String] {
    override val space = " "

    private def breakLine(w: Int)(s: String) = {
      val (lines, last) = s
        .split(" ")
        .toList
        .flatMap(s => if (s.size > w) s.grouped(w) else List(s))
        .foldLeft((List.empty[String], "")) { (state, s) =>
          val (lines, current) = state
          if (current.size + s.size + 1 <= w && !current.isEmpty)
            (lines, s"$current $s")
          else if (current.size + s.size + 1 <= w) (lines, s)
          else (current :: lines, s)
        }

      (last :: lines).reverse
    }

    override def apply(s: String)(w: Int): List[String] =
      if (w == 0) Nil
      else
        s.split("\n")
          .toList
          .flatMap(breakLine(w))
          .filter(!_.isEmpty)
          .map(_.padTo(w, ' '))
  }

  val basic = new Formatter[String] {
    override val space = " "
    override def apply(s: String)(w: Int): List[String] =
      if (w == 0) Nil
      else
        s.split("\n")
          .toList
          .flatMap(_.grouped(w).toList.map(_.padTo(w, ' ')))
  }

}
